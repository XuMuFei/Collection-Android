package com.youngmanster.collection.activity.base;

import android.Manifest;
import android.support.annotation.NonNull;

import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collectionlibrary.permission.PermissionManager;

/**
 * Created by yangyan
 * on 2018/3/23.
 */

public class PermissionActivity extends BaseActivity{

	//权限管理
	private PermissionManager permissionManager;

	// 项目的必须权限，没有这些权限会影响项目的正常运行
	private static final String[] PERMISSIONS = new String[]{
			Manifest.permission.READ_SMS,
			Manifest.permission.RECEIVE_WAP_PUSH,
	};
	//非必要权限，就算禁止对项目也没有多大影响
	private static final String[] PERMISSIONS1 = new String[]{
			Manifest.permission.ACCESS_FINE_LOCATION
	};

	@Override
	public int getLayoutId() {
		return R.layout.activity_permission;
	}

	@Override
	public void init() {
		setTitleContent(getString(R.string.activity_permission));
		showHomeAsUp(R.mipmap.ic_back_btn);
	}

	@Override
	public void requestData() {
		permissionManager=PermissionManager.with(this).
				setNonEssentialPermissions(PERMISSIONS1)
				//必须权限
				.setNecessaryPermissions(PERMISSIONS)
				//非必须权限

				.build();
	}


	//重写
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == PermissionManager.PERMISSION_REQUEST_CODE) {//PERMISSION_REQUEST_CODE为请求权限的请求值
			//有必须权限选择了禁止
			if (permissionManager.getShouldShowRequestPermissionsCode() == PermissionManager.EXIST_NECESSARY_PERMISSIONS_PROHIBTED) {
				showToast("可以在这里设置重新跳出权限请求提示框");
			} //有必须权限选择了禁止不提醒
			else if (permissionManager.getShouldShowRequestPermissionsCode() == PermissionManager.EXIST_NECESSARY_PERMISSIONS_PROHIBTED_NOT_REMIND) {
				showToast("可以在这里弹出提示框提示去应用设置页开启权限");
				permissionManager.startAppSettings();
			}
		}
	}
}