import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FitnessTrackerTest2 {

	private FitnessTrackerGUI fitnessTracker;

	@BeforeEach
	void setUp() {
		// Initializing a new instance
		fitnessTracker = new FitnessTrackerGUI();
	}

	@Test
	void testAddWorkout() {
		// adding a workout
		String workoutType = "Running";
		double caloriesBurned = 500;
		Date date = new Date();

		// Creating a workout manually
		Workout workout = new Workout(workoutType, caloriesBurned, date);

		// Adding the workout to the tracker
		fitnessTracker.workouts.add(workout);
		fitnessTracker.Entries.add(workout);

		// Verify the workouts
		assertTrue(fitnessTracker.workouts.contains(workout));
		assertEquals(1, fitnessTracker.workouts.size());
		assertEquals(1, fitnessTracker.Entries.size());
	}

	@Test
	void testAddMeal() {
		// adding a meal
		String mealName = "Lunch";
		double calories = 700;
		Date date = new Date();

		// Creating a meal
		Meal meal = new Meal(mealName, calories, date);

		// Adding the meal to the tracker
		fitnessTracker.meals.add(meal);
		fitnessTracker.Entries.add(meal);

		// Verifying the meal is added
		assertTrue(fitnessTracker.meals.contains(meal));
		assertEquals(1, fitnessTracker.meals.size());
		assertEquals(1, fitnessTracker.Entries.size());
	}

	@Test
	void testSortEntries() {
		// sample entries
		Workout workout1 = new Workout("Running", 500, new Date(1000000));
		Workout workout2 = new Workout("Cycling", 300, new Date(2000000));
		Meal meal = new Meal("Dinner", 600, new Date(1500000));

		// Adding entries to tracker
		fitnessTracker.Entries.add(workout1);
		fitnessTracker.Entries.add(workout2);
		fitnessTracker.Entries.add(meal);

		// Sorting entries by date
		fitnessTracker.sortEntries();

		assertEquals(workout1, fitnessTracker.Entries.get(0));
		assertEquals(meal, fitnessTracker.Entries.get(1));
		assertEquals(workout2, fitnessTracker.Entries.get(2));
	}

	@Test
	void testUndoAction() {

		String workoutAction = "Workout added: Running, 500 calories";
		String mealAction = "Meal added: Dinner, 600 calories";
		// Creating workout and meal objects
		Workout workout = new Workout("Running", 500, new Date());
		Meal meal = new Meal("Dinner", 600, new Date());

		// Pushing the objects to the action stack
		fitnessTracker.actionStack.push(workoutAction); // Push the actual object, not a string
		fitnessTracker.workouts.add(workout);
		fitnessTracker.Entries.add(workout);

		fitnessTracker.actionStack.push(mealAction); // Push the actual object, not a string
		fitnessTracker.meals.add(meal);
		fitnessTracker.Entries.add(meal);

		// Undoing the last action
		fitnessTracker.undoAction();

		assertEquals(1, fitnessTracker.actionStack.size());
		assertTrue(fitnessTracker.actionStack.peek().contains("Workout added")); // Ensure the stack holds a Workout
																					// object

		// Verify the meal was removed
		assertTrue(fitnessTracker.meals.isEmpty());
		assertEquals(1, fitnessTracker.Entries.size());
		assertEquals(workout, fitnessTracker.Entries.get(0));

		// Undo the second action
		fitnessTracker.undoAction();

	}
	    }