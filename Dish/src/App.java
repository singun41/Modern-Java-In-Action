import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH)
        );

        Optional<Dish> maxCalorieDish = menu.stream().collect(Collectors.maxBy(Comparator.comparing(Dish::getCalories)));
        System.out.println(maxCalorieDish.get().toString());
        System.out.println(System.lineSeparator());

        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(Collectors.groupingBy(Dish::getType));
        System.out.println(dishesByType.toString());
        System.out.println(System.lineSeparator());

        Map<Dish.Type, List<String>> dishesNameByType = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toList())));
        System.out.println(dishesNameByType.toString());
        System.out.println(System.lineSeparator());

        Map<Dish.Type, List<Dish>> calrolicDishesByType = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.filtering(d -> d.getCalories() > 500, Collectors.toList())));
        System.out.println(calrolicDishesByType.toString());
        System.out.println(System.lineSeparator());

        // Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
        Map<Dish.Type, Dish> mostCaloricByType = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(mostCaloricByType);
        System.out.println(System.lineSeparator());

        Map<Dish.Type, Set<CaloricLevel>> calcoricLevelsByType = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(dish -> {
            if(dish.getCalories() <= 400) return CaloricLevel.DIET;
            else if(dish.getCalories() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        }, Collectors.toCollection(HashSet::new))));
        System.out.println(calcoricLevelsByType);
        System.out.println(System.lineSeparator());

        Map<Boolean, List<Dish>> vegetarianMenu = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
        System.out.println(vegetarianMenu);
        System.out.println(System.lineSeparator());
        System.out.println(vegetarianMenu.get(true));
        System.out.println(System.lineSeparator());
        System.out.println(vegetarianMenu.get(false));
        System.out.println(System.lineSeparator());

        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));
        System.out.println(vegetarianDishesByType);
        System.out.println(System.lineSeparator());

        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(mostCaloricPartitionedByVegetarian);
        System.out.println(System.lineSeparator());
    }
}
