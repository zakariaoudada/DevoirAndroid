package devoir.android.emsi.ma.devoir;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.buttonCharger);
        if(isOnline())
        {
            btn.setEnabled(true);
        }
    }
    public void Tach(View v)
    {
        Appel appel = new Appel();
        appel.execute();
    }

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        return net != null && net.isConnected();
    }

    private class Appel extends AsyncTask<Void,Void,Etudiant>
    {


        @Override
        protected Etudiant doInBackground(Void... params) {
            Etudiant etudiant = new Etudiant();
            String login = "test";
            String mp = "test";
            String URL_login = "http://www.belatar.info/tests/profile.php";
            String resultat ="";


            try {
                /**
                 * url -> Post -> output -> $_POST -> envoyer -> fermeture
                 */
                URL url = new URL(URL_login);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("login","UTF-8")+"="+URLEncoder.encode(login,"UTF-8")+"&"+
                        URLEncoder.encode("passwd","UTF-8")+"="+URLEncoder.encode(mp,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                /**
                 * input -> tempon -> while readline
                 */
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String temp;

                while ((temp = bufferedReader.readLine()) != null)
                    buffer.append(temp);

                JSONObject objetjson = new JSONObject(buffer.toString());
                etudiant.setNom(objetjson.getString("nom"));
                etudiant.setPrenom(objetjson.getString("prenom"));
                etudiant.setClasse(objetjson.getString("classe"));

                JSONArray notes = objetjson.getJSONArray("notes");
                List<Note> listnote=new ArrayList();
                String libelle="";
                String score="";
                for(int i=0; i<notes.length(); i++) {
                    JSONObject json_data = notes.getJSONObject(i);
                    listnote.add(new Note(json_data.getString("label"),json_data.getDouble("score")));
                }
                etudiant.setNotes(listnote);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return etudiant;
        }

        @Override
        protected void onPostExecute(Etudiant etudiant) {
            TextView nom =(TextView)findViewById(R.id.nomValue) ;
            TextView prenom =(TextView)findViewById(R.id.prenomValue) ;
            TextView classe =(TextView)findViewById(R.id.classeValue) ;
            TextView list =(TextView)findViewById(R.id.textViewList) ;
            nom.setText(etudiant.getNom());
            prenom.setText(etudiant.getPrenom());
            classe.setText(etudiant.getClasse());
            String notes="";
            for(int i=0;i<etudiant.getNotes().size();i++)
            {
                notes+=etudiant.getNotes().get(i).getLabel().toString() +"   "+etudiant.getNotes().get(i).getScore().toString()+"\n";
            }
            list.setText(notes);
        }

    }

}
