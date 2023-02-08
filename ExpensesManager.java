import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@Deprecated
public class ExpensesManager {
    HashMap<String, ArrayList<Double>> expensesByCategories;

    ExpensesManager() {
        expensesByCategories = new HashMap<>();
    }

    public double saveExpense(double moneyBeforeSalary, String category, double expense) {
        moneyBeforeSalary = moneyBeforeSalary - expense;
        System.out.println("Значение сохранено! Ваш текущий баланс в рублях: " + moneyBeforeSalary);

        if (expensesByCategories.containsKey(category)) {
            ArrayList<Double> newExpense = expensesByCategories.get(category);
            newExpense.add(expense);
        } else {
            ArrayList<Double> newExpense = new ArrayList<>();
            newExpense.add(expense);
            expensesByCategories.put(category, newExpense);
        }

        if (moneyBeforeSalary < 1000) {
            System.out.println("На вашем счету осталось совсем немного. Стоит начать экономить!");
        }
        return moneyBeforeSalary;
    }

    public double findMaxExpenseInCategory(String category) {
        double maxExpense = 0;
        if (expensesByCategories.containsKey(category)) {
            ArrayList<Double> expenses = expensesByCategories.get(category);
            for (Double expense : expenses) {
                if (expense > maxExpense) {
                    maxExpense = expense;
                }
            }
        } else {
            System.out.println("Такой категории пока нет.");
        }

        return maxExpense;
    }

    public void printAllExpensesByCategories() {
        for (String category : expensesByCategories.keySet()) {
            System.out.println(category);
            for (Double expense : expensesByCategories.get(category)) {
                System.out.println(expense);
            }
        }
    }

    public void removeAllExpenses() {
        expensesByCategories.clear();
        System.out.println("Список трат пуст.");
    }

    public double getExpensesSum() {
        double sum = 0d;
        for (String category : expensesByCategories.keySet()) {
            for (Double expense : expensesByCategories.get(category)) {
                sum += expense;
            }
        }

        return sum;
    }

    public String getMaxCategoryName() {
        String maxCategoryName = "";
        double maxCategorySum = 0d;

        for (String category : expensesByCategories.keySet()) {
            double sum = 0d;
            for (Double expense : expensesByCategories.get(category)) {
                sum += expense;
                if (maxCategorySum < sum) {
                    maxCategorySum = sum;
                    maxCategoryName = category;
                }
            }
        }
        return maxCategoryName;
    }

    public void removeCategory(String category) {
        if (expensesByCategories.containsKey(category)) {
            expensesByCategories.remove(category);
            System.out.println("Категория " + category + " удалена!");
        } else {
            System.out.println("Такой категории не существует.");
        }
    }
}
