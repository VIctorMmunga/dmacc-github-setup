
import java.text.SimpleDateFormat;
import java.util.Date;

public class Workout extends Entry {
	private String type;
	private double caloriesBurned;
	private Date date;

	// Constructor to initialize a Workout object
	public Workout(String type, double caloriesBurned, Date date) {
		super(date);
		this.type = type;
		this.caloriesBurned = caloriesBurned;
		this.date = date;
	}

	// Getter for the workout type
	public String getType() {
		return type;
	}

	// Setter for the workout type
	public void setType(String type) {
		this.type = type;
	}

	// Getter for the calories burned
	public double getCaloriesBurned() {
		return caloriesBurned;
	}

	// Setter for the calories burned
	public void setCaloriesBurned(double caloriesBurned) {
		this.caloriesBurned = caloriesBurned;
	}

	@Override
	public String toString() {
		// Format the date for display
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "Workout: " + type + "\n" + "Calories Burned: " + caloriesBurned + "\n" + "Date: "
				+ dateFormat.format(getDate());
	}
}
