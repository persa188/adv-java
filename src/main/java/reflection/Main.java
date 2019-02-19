package reflection;

import org.junit.Before;
import org.junit.Test;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class Main {
    private static final String BLAKE_UUID_STRING = "38400000-8cf0-11bd-b23e-10b96e4ef00d";
    private static final String YANG_UUID_STRING = "38400000-8cf0-11bd-b23e-10b96e4ef00e";
    private static final Map<String, Object> FIELD_MAP_ONE = new HashMap<>();
    private static final Map<String, Object> FIELD_MAP_TWO = new HashMap<>();
    private Person blake;
    private Person yang;

    @Before
    public void setup() {
        blake = Person
                .builder()
                .age(19)
                .name("Blake Belladona")
                .sex("F")
                .guid(UUID.fromString(BLAKE_UUID_STRING))
                .build();

        yang = Person
                .builder()
                .age(19)
                .name("Yang Xiao Long")
                .sex("F")
                .guid(UUID.fromString(YANG_UUID_STRING))
                .build();

        blake.setInTeam(true);
        yang.setInTeam(true);

        // clean this up
        FIELD_MAP_ONE.put("name", "blake");
        FIELD_MAP_ONE.put("age", 19);
        FIELD_MAP_ONE.put("sex", "F");
        FIELD_MAP_ONE.put("guid", UUID.fromString(BLAKE_UUID_STRING));
        FIELD_MAP_ONE.put("inTeam", Optional.of(true));

        FIELD_MAP_TWO.put("name", "yang");
        FIELD_MAP_TWO.put("age", 20);
        FIELD_MAP_TWO.put("sex", "Female");
        FIELD_MAP_TWO.put("guid", UUID.fromString(YANG_UUID_STRING));
        FIELD_MAP_TWO.put("inTeam", Optional.of(false));
    }


    @Test
    public void setFieldsAndGenerateAssertionsForEquivalence() throws Exception {
        Class person = Person.class;
        Field[] fields = person.getFields();

        for (Field field: fields) {

            System.out.println("setting field "
                    + field.getName() + " with value " + FIELD_MAP_ONE.get(field.getName()));
            field.setAccessible(true);

            //set the fields to the same thing and run tests to compare them one by one for equivalence
            field.set(blake, FIELD_MAP_ONE.get(field.getName()));
            field.set(yang, FIELD_MAP_ONE.get(field.getName()));
        }

        compareAllFields(blake, yang, true);
    }

    @Test
    public void setFieldsAndGenerateAssertionsForDifference() throws Exception{
        Class person = Person.class;
        Field[] fields = person.getFields();

        for (Field field: fields) {

            //set the fields to the same thing and run tests to compare them one by one for equivalence
            field.set(blake, FIELD_MAP_ONE.get(field.getName()));
            field.set(yang, FIELD_MAP_TWO.get(field.getName()));
        }

        compareAllFields(blake, yang, false);
    }

    public void compareAllFields(Person p, Person q, boolean expectedValue) throws Exception{
        List<Method> readMethods = getAllReadMethodsForPersonClass();

        for (Method method: readMethods) {
            assertEquals(
                    "test " + method.getName() +"() for equivalence with two equivalent people... failed!",
                    expectedValue,
                    method.invoke(p).equals(method.invoke(q)));
        }
    }

    private List<Method> getAllReadMethodsForPersonClass() throws Exception {
        List<String> ignoredReadMethods = Arrays.asList("getClass");
        PropertyDescriptor[] descriptors = Introspector
                .getBeanInfo(Person.class)
                .getPropertyDescriptors();

        List<Method> readMethods = new ArrayList<>();
        for (PropertyDescriptor p: descriptors) {
            //ignore the getClass method as it's not relevant to Person Object
            if (!ignoredReadMethods.contains(p.getReadMethod().getName())) {
                readMethods.add(p.getReadMethod());
            }
        }

        return readMethods;
    }

//    private void setAndCompareSingleAttributeForPersons() throws Exception {
//       System.out.println(blake.getClass().getFields());
//    }

//    private void setFieldForPerson(Field field, Person person, Object value) throws Exception {
//        Class personClass = Person.class;
//        field.set(person, value);
//    }

//    /**
//     * prints all the attributes for a team of people
//     * @param team a list of People (i.e. Person.class)
//     * @throws Exception
//     */
//    private void printAllAttributesForTeam(List<Person> team) throws Exception {
//
//        System.out.println("Printing attributes for team: " + team);
//        //get a list of getter methods
//        List<Method> readMethods = getAllReadMethodsForPersonClass();
//
//        //print all getter methods for everyone in the team
//        for (Person person: team) {
//            System.out.println("--------------------------");
//            readMethods.stream().forEach(method -> {
//                try {
//                    System.out.println(
//                            method.getName()
//                                    + ": "
//                                    + method.invoke(person));
//                } catch (Exception e) { e.printStackTrace(); }
//            });
//        }
//    }
}
