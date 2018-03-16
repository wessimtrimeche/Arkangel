package prx.test.kotlin.arkangel.data

import com.google.firebase.auth.FirebaseAuth

/**
 * Created by wessim23 on 3/5/18.
 */

class DataManagerAccessor : DataManager(){

    //appel lel datamanager
// instance fl presenter


    fun _createUser(mAuth: FirebaseAuth, firstName: String, lastName: String) {
        createUser(mAuth, firstName,lastName)
    }

}