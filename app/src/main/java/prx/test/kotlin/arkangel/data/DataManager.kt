package prx.test.kotlin.arkangel.data

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
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
        var displayName = mAuth.currentUser?.displayName

        user.firstName = firstName
        user.lastName = lastName
        displayName= user.firstName+" "+user.lastName

        val userEmail = mAuth.currentUser?.email
        user.email = userEmail

        val ref: DatabaseReference = database.getReference("users/" + displayName)
        ref.setValue(user)
    }


    fun createUserGoogle(mAuth: FirebaseAuth,account: GoogleSignInAccount) {

        val user = User()

        var database = FirebaseDatabase.getInstance()
        val userId = mAuth.currentUser?.uid;
        var displayName = mAuth.currentUser?.displayName
//        val firstName1 = firstnameEditText.text.toString()
//        val lastName1 = lastnameEditText.text.toString()

        user.firstName = account.givenName
        user.lastName = account.familyName

        displayName= user.firstName+" "+user.lastName
        val userEmail = mAuth.currentUser?.email
        user.email = userEmail

        val ref: DatabaseReference = database.getReference("users/" + displayName)
        ref.setValue(user)
    }

}
