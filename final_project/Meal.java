import java.io.Serializable;
import java.util.Date;

public class Meal extends Entry {
	private String name;
	private double calories;
	private Date date;

	// construct to initialize meal
	public Meal(String name, double calories, Date date) {
		super(date);
		this.name = name;
		this.calories = calories;
		this.date = date;

	}

	// setter for name
	public String getName() {
		return name;
	}

	// setter for calories
	public double getCalories() {
		return calories;
	}

	// setter for date
	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "MealName: " + name + "\n" + "Calories: " + calories + "\n" + "Date: " + date + "\n";

	}
}
