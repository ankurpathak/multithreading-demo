package com.github.ankurpathak;

public class Employee {
    private int id;
    private String email;
    private String name;


    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        if(this == obj) return true;
        Employee objEmp = (Employee) obj;
        return this.id == objEmp.id && this.email.equals(objEmp.email) && this.name.equals(objEmp.name);
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + id;
        hash = hash * 31 + (email == null ? 0 : email.hashCode());
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        return hash;
    }
}

//singelton, garbage collector, solid, factory and abstract factory
//promice and async await
//immutable
//list and map immutable
//merge and quick sort
//fork and join


//immutable class
//1. Class is final
//2. Fields are private and final
//3. Parameterized Constructor
//4. No Setter
//5. Getter should deep clones to return
//6. Method should be final
