package me.herrlestrate.kadushko_artyom_info.fragments.launcher;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import me.herrlestrate.kadushko_artyom_info.Consts;
import me.herrlestrate.kadushko_artyom_info.R;
import me.herrlestrate.kadushko_artyom_info.fragments.dekstop.DesktopFragment;

public class LauncherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
    protected ResolveInfo mResolveInfo;

    private final ImageView mAppIcon;
    private final TextView mAppTitle;

    int mIconLayoutId;
    int mTitleLayoutId;

    private final View mView;

    public LauncherViewHolder(@NonNull View itemView, int icon_layout_id, int title_layout_id) {
        super(itemView);
        Log.i("LauncherActivity","LauncherViewHolder");
        mView = itemView;
        mAppIcon = itemView.findViewById(icon_layout_id);
        mAppTitle = itemView.findViewById(title_layout_id);

        mIconLayoutId = icon_layout_id;
        mTitleLayoutId = title_layout_id;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void bind(ResolveInfo resolveInfo, Drawable icon) {
        Log.i("LauncherActivity","bind: "+resolveInfo.resolvePackageName);
        mResolveInfo = resolveInfo;
        PackageManager pm = mView.getContext().getPackageManager();
        String appName = mResolveInfo.loadLabel(pm).toString();

        mAppIcon.setImageDrawable(icon);
        mAppTitle.setText(appName);
    }

    @Override
    public void onClick(View view){
        Log.i("LauncherViewHolder","Clicked!");
        ActivityInfo activityInfo = mResolveInfo.activityInfo;

        Consts.add(activityInfo.applicationInfo.packageName);

        Intent i = new Intent(Intent.ACTION_MAIN)
                .setClassName(activityInfo.applicationInfo.packageName,activityInfo.name)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        view.getContext().startActivity(i);
    }

    public boolean showPopUp(View v){
        PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popup,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_uninstall_app:
                        uninstallApplication();
                        return true;

                    case R.id.action_open_app_settings:
                        openApplicationSetting();
                        return true;
                    case R.id.action_add_on_desktop:
                        addOnDesktop();
                        return true;
                    default:
                        return false;
                }
            }
        });

        MenuItem launchCountMenu = popupMenu.getMenu().findItem(R.id.action_count_app_launches);
        launchCountMenu.setTitle(v.getResources().getString(R.string.frequency)+Consts.get(mResolveInfo.activityInfo.applicationInfo.packageName));
        launchCountMenu.setEnabled(false);
        popupMenu.show();
        return true;
    }

    public void addOnDesktop(){
        Log.i("Desktop","Add on desktop");
        for(int i=0;i < 5 ; i++){
            for(int j=0;j< 4; j++){
                String name = Consts.getByPos(i,j);
                if(name == "none"){
                    Consts.setAppLocation(mResolveInfo.activityInfo.packageName,i,j);
                    DesktopFragment.update(Consts.getDesktopView(),Consts.getDesktopInflater(),Consts.getActivity());
                    return;
                }
            }
        }
    }

    public void uninstallApplication(){
        Log.i("Uninstall",mResolveInfo.activityInfo.packageName);
        Uri packageURI = Uri.parse("package:"+mResolveInfo.activityInfo.packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE,packageURI);
        mView.getContext().startActivity(intent);
    }

    public void openApplicationSetting(){
        Uri packageURI = Uri.parse("package:"+mResolveInfo.activityInfo.packageName);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(packageURI);
        mView.getContext().startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        /*ClipData.Item type = new ClipData.Item("type");
        ClipData.Item data = new ClipData.Item("data");

        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

        ClipDescription description = new ClipDescription("",mimeTypes);

        ClipData dragData = new ClipData(description,type);
        dragData.addItem(data);

        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(mView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mView.startDragAndDrop(dragData,shadowBuilder,mView,0);

        }*/

        return showPopUp(v);
    }
}
