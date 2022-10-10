package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.LogInOutService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter extends Presenter {
    private static final String LOG_TAG = "RegisterFragment";
    private View view;
    LogInOutService registerService;

    public RegisterPresenter(View view) {
        super(view);
        this.view = view;
        registerService = new LogInOutService();
    }

    public interface View extends Presenter.View{

        void startMain(User registeredUser);
    }

    public void validateRegistration(Editable firstName, Editable lastName, Editable alias, EditText password, ImageView imageToUpload) {
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (alias.length() == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        if (imageToUpload.getDrawable() == null) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }
    public void RegisterUser(ImageView imageToUpload, String firstName, String lastName, String alias,
                             String password){
        Bitmap image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageBytes = bos.toByteArray();

        // Intentionally, Use the java Base64 encoder so it is compatible with M4.
        String imageBytesBase64 = Base64.getEncoder().encodeToString(imageBytes);

        // Send register request.

        registerService.StartRegisterTask(firstName, lastName, alias, password, imageBytesBase64,
                new RegisterHandlerObserver());
//        RegisterTask registerTask = new RegisterTask(firstName, lastName,
//                alias, password, imageBytesBase64, new RegisterHandler());
//
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(registerTask);
    }

    public class RegisterHandlerObserver extends Presenter.ServiceObserver implements LogInOutService.RegisterHandlerObserver{

        @Override
        public void registerSuccess(Bundle data) {
            User registeredUser = (User) data.getSerializable(RegisterTask.USER_KEY);
            AuthToken authToken = (AuthToken) data.getSerializable(RegisterTask.AUTH_TOKEN_KEY);

            Cache.getInstance().setCurrUser(registeredUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);

            view.startMain(registeredUser);
//            Intent intent = new Intent(getContext(), MainActivity.class);
//            intent.putExtra(MainActivity.CURRENT_USER_KEY, registeredUser);
//
//            registeringToast.cancel();
//
//            Toast.makeText(getContext(), "Hello " + Cache.getInstance().getCurrUser().getName(), Toast.LENGTH_LONG).show();
//            try {
//                startActivity(intent);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
        }
    }

}
