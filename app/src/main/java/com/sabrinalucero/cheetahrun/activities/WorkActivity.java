package com.sabrinalucero.cheetahrun.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sabrinalucero.cheetahrun.R;
import com.sabrinalucero.cheetahrun.models.Work;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabrina on 19/07/2017.
 */

public class WorkActivity extends AppCompatActivity{

  private ListView listView;
  private List<Work> works;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.fragment_second);

    listView = (ListView) findViewById(R.id.listView);

    List<String> worksout = new ArrayList<>();
    worksout.add("Plan Básico");
    worksout.add("Caminatas 30 min");
    worksout.add("Plan Entrenamiento Avanzado");
    worksout.add("Caminatas 10 min + correr 30 min ");
    worksout.add("Plan Rutinas Diarias");
    worksout.add("Caminatas 20 min + correr 20 min ");

    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, worksout);


    listView.setAdapter(adapter);
  }
}
