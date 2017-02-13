package funsooyenuga.numericalanalysis.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;

public class AboutActivity extends AppCompatActivity {

    private String email = "OyenugaFunso@gmail.com";
    private String repoUrl = "FunsoFab/NumericalAnalysis";
    private String description = "This app is open source and under development.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(description)
                .addEmail(email)
                .addGitHub(repoUrl)
                .create();

        setContentView(aboutPage);
    }
}
