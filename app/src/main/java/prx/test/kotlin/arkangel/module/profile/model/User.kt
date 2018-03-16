package prx.test.kotlin.arkangel.module.profile.model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by wessim23 on 3/5/18.
 */

class User {

    var firstName: String? = null
    var lastName: String? = null
    var accountType: String? = null
    var email: String? = null
    var password: String? = null
    //
    //    public void addChild(Child child){
    //        childList.add(child);
    //    }

    var childList: MutableList<Child>? = null

    fun getchildsList(): MutableList<Child>? {

        return childList
    }
}
