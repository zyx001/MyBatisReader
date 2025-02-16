package zyp.testDemo;

import java.lang.reflect.Field;

public class ReflectionTest {


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Child child = new Child();
        Class<? super Child> superclass = Child.class.getSuperclass();

        Field parentName = superclass.getSuperclass().getDeclaredField("parentName");
        parentName.setAccessible(true);
        parentName.set(child, "B");

        Field parent = superclass.getDeclaredField("parentName");
        parent.setAccessible(true);
        parent.set(child, "A");
        System.out.println(parent.equals(parentName));

        System.out.println(child.getParentName());
    }


}
class Parent extends Grant{
    private String name;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    private String age;
    private int sex;

    private String parentName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}


class Child extends Parent{

    @Override
    public String toString() {
        return "Child{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }

    private String name;
    private Integer age;
    private int sex;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    private String schoolName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setAge(Integer age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}

class Grant{
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    private String parentName;

}