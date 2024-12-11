/**
* FitnessTracker App
*
* @author Victor Mmunga
* @version 1.0
* @since 1.0
*/
/*
* OS: Windows 10 IDE: 
* eclipse 2024-12
* Copyright : This is my own original work
* based on specifications issued by our instructor Academic Honesty: I attest
* that this is my original work. I have not used unauthorized source code,
* either modified or unmodified, nor used generative AI as a final draft. I
* have not given other fellow student(s) access to my program.
*/

import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat; // formatting dates
import java.util.Date;// date class handling date/time values

// The main class of application
public class FitnessTracker {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new FitnessTrackerGUI());
	}
}

//class that contains the application logic
class FitnessTrackerGUI {
	public List<Entry> Entries;// list to store all entries (workouts and meals)
	public JFrame frame;
	public JTextArea logArea;
	public List<Workout> workouts;
	public List<Meal> meals;
	public Queue<String> entryQueue;
	public Stack<String> actionStack;// store action for undo functionality
	public int sortIndex;// to tracl the current position while sorting entries
	public List<String> sortedEntries;

	// GUI component and date structure
	public FitnessTrackerGUI() {

		workouts = new ArrayList<>();
		meals = new ArrayList<>();
		entryQueue = new LinkedList<>();
		actionStack = new Stack<>();
		sortIndex = 0; // Initialize the index to 0
		sortedEntries = new ArrayList<>(); // Initializing the sorted entries list
		Entries = new ArrayList<>();// list of entries
		setupGUI();// calling the method to set up the GUI
	}

	// Setting up the GUI
	public void setupGUI() {
		frame = new JFrame("Fitness Tracker App");// MAin window frame
		// close the application when the widindow is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);// window size
		// creating a panel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		// text area for entries and make it non-edited
		logArea = new JTextArea();
		logArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(logArea);
		// panel for the input buttons
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2, 2));
//  add workouts , add meals, undoing actions, and sorting Entries buttons
		JButton addWorkoutButton = new JButton("Add Workout");
		JButton addMealButton = new JButton("Add Meal");
		JButton undoButton = new JButton("Undo");
		JButton sortButton = new JButton("Sort Entries");
		// EVENT LISTENER for each button to perform actions
		addWorkoutButton.addActionListener(e -> addWorkout());
		addMealButton.addActionListener(e -> addMeal());
		undoButton.addActionListener(e -> undoAction());
		sortButton.addActionListener(e -> sortEntries());
//adding the button to the input panel
		inputPanel.add(addWorkoutButton);
		inputPanel.add(addMealButton);
		inputPanel.add(undoButton);
		inputPanel.add(sortButton);
// adding the components to the main panel
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(inputPanel, BorderLayout.SOUTH);
// making it visible
		frame.add(panel);
		frame.setVisible(true);
	}

// add workout  method
	public void addWorkout() {
		// getting the workout type and calories burned from the user
		String type = JOptionPane.showInputDialog("Enter workout type:");
		String caloriesInput = JOptionPane.showInputDialog("Enter calories burned:");
		if (type == null || caloriesInput == null) {
			return;
		}
		// Converting calories input to a number
		double calories;
		try {
			calories = Double.parseDouble(caloriesInput);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Calories must be a valid number.", "Input Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		// worlout entry with the type,calories, and current date
		Date date = new Date();
		Workout workout = new Workout(type, calories, date);
		workouts.add(workout);
		Entries.add(workout);
		entryQueue.add(workout.toString());
		actionStack.push("Workout added: " + workout);
		logArea.append(workout + "\n");
		System.out.println("Workouts list: " + workouts);

	}

// Method to add a meal entry
	public void addMeal() {
		// getting meal name and calories from the user
		String name = JOptionPane.showInputDialog("Enter meal name:");
		String caloriesInput = JOptionPane.showInputDialog("Enter calories:");
		if (name == null || caloriesInput == null) {
			return;
		}
		// convert input to a number
		double calories;
		try {
			calories = Double.parseDouble(caloriesInput);
		} catch (NumberFormatException e) {
			// error message if calories are invalid
			JOptionPane.showMessageDialog(frame, "Calories must be a valid number.", "Input Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		// meal entry with the name, calories, and current date
		Date date = new Date();

		Meal meal = new Meal(name, calories, date);
		Entries.add(meal);

		entryQueue.add(meal.toString());
		actionStack.push("Meal added: " + meal);
		logArea.append(meal + "\n");

		System.out.println("Meals list: " + meals);// printing for debugging
	}

	// undo method , undoing the last action
	public void undoAction() {
		if (!actionStack.isEmpty()) {
			// getting the last action from stack
			String lastAction = actionStack.pop();
			logArea.append("Undo: " + lastAction + "\n");

			// undo the workout entry
			if (lastAction.contains(" Workout")) {

				if (!workouts.isEmpty()) {
					Workout lastWorkout = workouts.remove(workouts.size() - 1);
					Entries.remove(Entries.size() - 1);
				}
				// undo the meal
			} else if (lastAction.contains("Meal")) {
				// Remove the last meal
				if (!meals.isEmpty()) {
					Meal lastMeal = meals.remove(meals.size() - 1);
					Entries.remove(lastMeal);
				}

			} else {
				logArea.append("Nothing to undo!\n");
			}
		} else {
			logArea.append("Nothing to undo!\n");
		}
	}

	// Method to sort entries by date
	public void sortEntries() {

		// Remove entries with null dates
		Entries.removeIf(entry -> entry.getDate() == null);

		// Sorting entries by date
		Entries.sort(Comparator.comparing(Entry::getDate));
		// Clear the log area to avoid displaying old data multiple times
		logArea.setText("");
		logArea.append("Sorted Entries:\n");
		for (Entry entry : Entries) {
			logArea.append(entry + "\n");
		}

		// index for sorting if needed
		if (sortIndex < Entries.size()) {
			logArea.append(Entries.get(sortIndex) + "\n");
			sortIndex++;
		} else {
			logArea.append("All sorted entries displayed.\n");
		}
	}
}
