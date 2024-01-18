package com.mycompany.digitaltwin;

import java.util.Comparator;

public class CustomerGroupComparator implements Comparator<CustomerGroup> {

    @Override
    public int compare(CustomerGroup o1, CustomerGroup o2) {
        return o1.getArrivingTime() - o2.getArrivingTime();
    }
}
