package prx.test.kotlin.arkangel.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kelvinapps.rxfirebase.RxFirebaseAuth
import prx.test.kotlin.arkangel.module.profile.model.User
import rx.functions.Action0
import rx.functions.Action1

/**
 * Created by wessim23 on 3/5/18.
 */

open  class DataManager

/*public usrlogin(){
    networkprovider.login()
    }*///rxjava fl onComplete sauvegarde fl cache par ex
{



    fun createUser(mAuth: FirebaseAuth, firstName: String, lastName: String) {


        val user = User()

        var database = FirebaseDatabase.getInstance()
        val userId = mAuth.currentUser?.uid;
//        val firstName1 = firstnameEditText.text.toString()
//        val lastName1 = lastnameEditText.text.toString()

        user.firstName = firstName
        user.lastName = lastName

        val userEmail = mAuth.currentUser?.email
        user.email = userEmail

        val ref: DatabaseReference = database.getReference("users/" + userId)
        ref.setValue(user)
    }

}
