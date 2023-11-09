package com.aa.act.interview.org;

import java.util.Optional;
import java.util.UUID;

public abstract class Organization {

    private Position root;

    private int employeeId = 1;
    
    public Organization() {
        root = createOrganization();
    }
    
    protected abstract Position createOrganization();
    
    /**
     * hire the given person as an employee in the position that has that title
     * 
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
    public Optional<Position> hire(Name person, String title) {
        //your code here
       return fillPosition(root, person, title);
    }

    @Override
    public String toString() {
        return printOrganization(root, "");
    }
    
    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for(Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "  "));
        }
        return sb.toString();
    }

    public Optional<Position> fillPosition (Position position, Name name, String title) {
        if (position.getTitle().equals(title)) {
            Employee employee = new Employee(employeeId++, name);
            position.setEmployee(Optional.of(employee));
            return Optional.of(position);
        } else {
            for (var child : position.getDirectReports()) {
                var result = fillPosition(child, name, title);
                if (result.isPresent()) {
                    return result;
                }
            }
        }
        return Optional.empty();
    }

}
