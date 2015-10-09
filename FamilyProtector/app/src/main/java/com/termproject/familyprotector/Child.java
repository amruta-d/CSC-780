package com.termproject.familyprotector;

/**
 * Created by Mehul on 10/8/2015.
 */
public class Child  {
    public static String name, gender, birthdate, age;

    public Child (AddChildDetails childDetails){
        this.name = childDetails.editTextChildName.getText().toString();
        if (childDetails.radioButtonMale.isChecked()){
            this.gender = "Male";

        }
        else if (childDetails.radioButtonFemale.isChecked()){
            this.gender = "Female";
        }

        this.birthdate = childDetails.editTextBirthDate.getText().toString();
//        DateFormat format = new SimpleDateFormat("MM/DD/YYYY", Locale.ENGLISH);
//        Date date =  format.parse(birthdate);

      //  Log.d("Child", name+gender+birthdate);




    }
}
