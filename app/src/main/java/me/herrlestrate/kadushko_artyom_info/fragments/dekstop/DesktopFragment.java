package me.herrlestrate.kadushko_artyom_info.fragments.dekstop;

import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yandex.metrica.YandexMetrica;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import me.herrlestrate.kadushko_artyom_info.ApplicationBroadcaster;
import me.herrlestrate.kadushko_artyom_info.BackgroundManager;
import me.herrlestrate.kadushko_artyom_info.BackgroundReceiver;
import me.herrlestrate.kadushko_artyom_info.Consts;
import me.herrlestrate.kadushko_artyom_info.R;
import me.herrlestrate.kadushko_artyom_info.fragments.launcher.SquareView;

public class DesktopFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    protected BackgroundReceiver mBackgroundReceiver;

    public void onCreate(Bundle savedInstanceState){
        sharedPreferences = getActivity().getSharedPreferences("me.herrlestrate.kadushko_artyom_info_preferences",0);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), Consts.getTheme(getActivity()));

        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        getActivity().setTheme(Consts.getTheme(getActivity()));

        Consts.setDesktopView(localInflater.inflate(R.layout.desktop_layout,container,false));
        final View result = Consts.getDesktopView();

        /*new Thread() {
            View res = Consts.getDesktopView();
            public void run() {
                try {
                    System.out.println("1");
                    URL urll = new URL("https://loremflickr.com/720/1080/");
                    InputStream in = new BufferedInputStream(urll.openStream());
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    byte data[] = new byte[16384];
                    int nRead;
                    do {
                        nRead = in.read(data);
                        if (nRead != -1)
                            buffer.write(data, 0, nRead);
                    } while (nRead != -1);
                    buffer.flush();
                    byte b[] = buffer.toByteArray();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

                    Drawable drawable = new BitmapDrawable(result.getResources(), bitmap);
                    //YandexMetrica.reportEvent("Drawable: "+drawable.toString());
                    res.setBackground(drawable);
                    System.out.println(bitmap);
                    //result.setBackground(getResources().getDrawable(R.drawable.ava_circle));
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    //YandexMetrica.reportError("MalformedURLException",e);
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    //YandexMetrica.reportError("IOException",e);
                    e.printStackTrace();
                }
            }
        }.start();*/
        int height = 5;
        int width = 4;
        TableLayout tableLayout = result.findViewById(R.id.table);
        String path = tableLayout.getContext().getFilesDir().toString()  + this.getClass().toString() + ".png";
        BackgroundManager.startJobService(tableLayout,path);
        mBackgroundReceiver = new BackgroundReceiver(tableLayout,path);
        getActivity().sendBroadcast(new Intent());
        for(int posX = 0;posX < height; posX++){

            TableRow row = new TableRow(result.getContext());
            row.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,0f));


            for(int posY = 0; posY < width; posY++){
                /*SquareView squareView = new SquareView(getContext());
                squareView.setImageResource(R.drawable.ic_launcher_foreground);
                row.addView(squareView,posY);*/
                final String package_name = Consts.getByPos(posX,posY);
                final View view = inflater.inflate(R.layout.table_layout_item, row, false);
                SquareView im = view.findViewById(R.id.square_image);
                TextView text = view.findViewById(R.id.desktop_app_name);
                view.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(package_name);
                    }
                });
                if(package_name.startsWith("[URL]:")){
                    String args[] = package_name.split(":");
                    String name = args[1];
                    final String url = args[2];
                    Picasso.get().load("https://favicon.yandex.net/favicon/"+url+"?size=120").into(im);
                    text.setText(name);
                    final int pX = posX, pY = posY;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www."+url));
                            startActivity(browserIntent);
                        }
                    });
                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                            MenuInflater menuInflater = popupMenu.getMenuInflater();
                            menuInflater.inflate(R.menu.desktop_popup,popupMenu.getMenu());

                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.action_delete_from_desktop:
                                            Consts.removeByPos(pX,pY);
                                            Consts.getDesktopPagerAdapter().notifyDataSetChanged();
                                            return true;
                                        default:
                                            return false;
                                    }
                                }
                            });
                            popupMenu.show();
                            return true;
                        }
                    });
                }else if(package_name == "none") {
                    im.setImageResource(R.drawable.ava_circle);
                    im.setVisibility(View.INVISIBLE);
                    text.setText("");
                    final int pX = posX, pY = posY;
                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            final LayoutInflater inflater = getActivity().getLayoutInflater();
                            final View vDialog = inflater.inflate(R.layout.url_popup,null);
                            builder.setView(vDialog)
                                .setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EditText t = vDialog.findViewById(R.id.url_name);
                                        EditText t2 = vDialog.findViewById(R.id.url_url);
                                        Consts.setAppLocation("[URL]:"+t.getText()+":"+t2.getText(),pX,pY);
                                        Consts.getDesktopPagerAdapter().notifyDataSetChanged();
                                    }
                                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.create().show();
                            return false;
                        }
                    });
                }else{
                    //TODO get activity info and get logo for package name
                    final PackageManager pm = getActivity().getPackageManager();
                    try {
                        final ApplicationInfo info = pm.getApplicationInfo(package_name,0);
                        im.setImageDrawable(info.loadIcon(pm));
                        List<ResolveInfo> act = pm.queryIntentActivities((new Intent(Intent.ACTION_MAIN)).addCategory(Intent.CATEGORY_LAUNCHER),0);
                        ResolveInfo t = null;
                        for(ResolveInfo rs : act)if(rs.activityInfo.applicationInfo.packageName.equals(info.packageName))t = rs;
                        final ResolveInfo resolveInfo = t;

                        text.setText(pm.getApplicationLabel(info));
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Consts.add(info.packageName);

                                Intent i = new Intent(Intent.ACTION_MAIN)
                                        .setClassName(info.packageName,resolveInfo.activityInfo.name)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                v.getContext().startActivity(i);
                            }
                        });

                        final int pX = posX, pY = posY;
                        view.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                                MenuInflater menuInflater = popupMenu.getMenuInflater();
                                menuInflater.inflate(R.menu.desktop_popup,popupMenu.getMenu());

                                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        switch (item.getItemId()) {
                                            case R.id.action_delete_from_desktop:
                                                Consts.removeByPos(pX,pY);
                                                Consts.getDesktopPagerAdapter().notifyDataSetChanged();
                                                return true;
                                            default:
                                                return false;
                                        }
                                    }
                                });
                                popupMenu.show();
                                return true;
                            }
                        });
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                        YandexMetrica.reportError("Error at packageNameNotFound (Desktop) ",e);
                        im.setImageResource(R.drawable.ava_circle);
                        im.setVisibility(View.INVISIBLE);
                        text.setText("");
                        final int pX = posX, pY = posY;
                        view.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                                MenuInflater menuInflater = popupMenu.getMenuInflater();
                                menuInflater.inflate(R.menu.desktop_popup,popupMenu.getMenu());

                                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        switch (item.getItemId()) {
                                            case R.id.action_delete_from_desktop:
                                                Consts.removeByPos(pX,pY);
                                                Consts.getDesktopPagerAdapter().notifyDataSetChanged();
                                                return true;
                                            default:
                                                return false;
                                        }
                                    }
                                });
                                popupMenu.show();
                                return true;
                            }
                        });
                    }
                }
                row.addView(view);
            }
            tableLayout.addView(row,posX);
        }
        return result;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

    }

    public static DesktopFragment newInstance(){
        return new DesktopFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public static boolean isJobServiceOn( Context context ) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService( Context.JOB_SCHEDULER_SERVICE ) ;

        boolean hasBeenScheduled = false ;

        for ( JobInfo jobInfo : scheduler.getAllPendingJobs() ) {
            if ( jobInfo.getId() == 0 ) {
                hasBeenScheduled = true ;
                break ;
            }
        }

        return hasBeenScheduled ;
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart Desktop");
        IntentFilter filter = new IntentFilter(BackgroundReceiver.UPDATE_BACKGROUND);
        filter.addAction(BackgroundReceiver.UPDATE_BACKGROUND_ONCE);
        getContext().registerReceiver(mBackgroundReceiver,filter);
        getContext().sendBroadcast(new Intent(BackgroundReceiver.UPDATE_BACKGROUND));
    }

    /*@Override
    public void onStop() {
        super.onStop();
        YandexMetrica.reportEvent("Stopped desktop Fragment!");
        getContext().unregisterReceiver(mBackgroundReceiver);
    }*/
}
