package tutorial.extension.other.impl;

import tutorial.extension.other.SomeOtherAnnotation;
import tutorial.extension.other.package1.Bar1;
import tutorial.extension.other.package2.Bar2;

@SomeOtherAnnotation(barClasses = {Bar1.class, Bar2.class})
public class SomeOtherComponent {

}
