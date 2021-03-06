/*
 * Copyright 2013-2015 microG Project Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.microg.gms.maps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;

public class MapFragmentImpl extends IMapFragmentDelegate.Stub {

    private GoogleMapImpl map;
    private GoogleMapOptions options;
    private Context context;

    public MapFragmentImpl(Activity activity) {
        context = activity;
    }

    private GoogleMapImpl myMap() {
        if (map == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            map = new GoogleMapImpl(inflater, options);
        }
        return map;
    }

    @Override
    public IGoogleMapDelegate getMap() throws RemoteException {
        return myMap();
    }

    @Override
    public void onInflate(IObjectWrapper activity, GoogleMapOptions options,
            Bundle savedInstanceState) throws RemoteException {
        Log.d("MapFragmentImpl", "onInflate");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) throws RemoteException {
        //myMap().onCreate(savedInstanceState);
        Log.d("MapFragmentImpl", "onCreate");
    }

    @Override
    public IObjectWrapper onCreateView(IObjectWrapper layoutInflater, IObjectWrapper container,
            Bundle savedInstanceState) throws RemoteException {
        if (map == null) {
            LayoutInflater inflater = (LayoutInflater) ObjectWrapper.unwrap(layoutInflater);
            map = new GoogleMapImpl(inflater, options);
            //map.onCreate(savedInstanceState);
        } else {
            View view = map.getView();
            if (view.getParent() instanceof ViewGroup) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
        return ObjectWrapper.wrap(myMap().getView());
    }

    @Override
    public void onResume() throws RemoteException {
        myMap().onResume();
    }

    @Override
    public void onPause() throws RemoteException {
        myMap().onPause();
    }

    @Override
    public void onDestroyView() throws RemoteException {

    }

    @Override
    public void onDestroy() throws RemoteException {
        myMap().onDestroy();
    }

    @Override
    public void onLowMemory() throws RemoteException {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) throws RemoteException {
        //myMap().onSaveInstanceState(outState);
    }

    @Override
    public boolean isReady() throws RemoteException {
        Log.d("MapFragmentImpl", "isReady");
        return false;
    }
}
