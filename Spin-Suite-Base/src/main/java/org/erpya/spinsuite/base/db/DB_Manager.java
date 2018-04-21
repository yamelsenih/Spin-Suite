/*************************************************************************************
 * Product: Spin-Suite (Mobile Suite)                       		                 *
 * This program is free software; you can redistribute it and/or modify it    		 *
 * under the terms version 2 or later of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope   		 *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied 		 *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           		 *
 * See the GNU General Public License for more details.                       		 *
 * You should have received a copy of the GNU General Public License along    		 *
 * with this program; if not, write to the Free Software Foundation, Inc.,    		 *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     		 *
 * For the text or an alternative of this public license, you may reach us    		 *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpya.com				  		                 *
 *************************************************************************************/
package org.erpya.spinsuite.base.db;

import android.content.Context;
import android.util.Log;

import org.erpya.spinsuite.base.model.PO;

import org.erpya.spinsuite.base.util.Util;

import java.lang.reflect.Constructor;

/**
 * Database Manager (Handle support for distinct database)
 *
 * @author yamel, ysenih@erpya.com , http://www.erpya.com
 * <li> FR [  ]
 * @see https://github.com/erpcya/Spin-Suite/issues/
 */
public final class DB_Manager {

    /**
     * Private constructor for evelope
     * @param context
     */
    private DB_Manager(Context context) {
        this.context = context;
    }

    /** Private context */
    private Context context = null;
    /** Instance of this    */
    private static DB_Manager instance = null;
    /** Database reference  */
    private DB_Support database = null;
    /** Supported Database  */
    private final int COUCH_BASE_LITE = 0;
    /** Supported DB    */
    private static final String[] SUPPORTED_DATABASE = new String[]
            {       //  Couch Base Lite for Android
                    "org.erpya.spinsuite.base.db.CouchDBLite_2_0_Support"
            };

    /**
     * Get instance for it (Create new instance if it does not exist)
     * @param context
     * @return
     */
    public static DB_Manager getInstance(Context context) {
        if(instance == null) {
            instance = new DB_Manager(context);
        }
        //  Default return
        return instance;
    }

    /**
     * Get Database
     * @return
     * @throws Exception
     */
    private DB_Support getDatabase() throws Exception {
        if(database == null) {
            loadClass();
        }
        //  Default return
        return database;
    }

    /**
     * Get context
     * @return
     */
    public Context getContext() {
        return context;
    }

    /**
     * Get class name for instance
     * @return
     */
    private String getClassname() {
        return SUPPORTED_DATABASE[COUCH_BASE_LITE];
    }

    /**
     * Get Class from device type, used for handler
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return
     * @return Class<?>
     */
    private Class<?> getHandlerClass() {
        String className = getClassname();
        //	Validate null values
        if(Util.isEmpty(className)) {
            return null;
        }
        try {
            Class<?> clazz = Class.forName(className);
            if(DB_Support.class.isAssignableFrom(clazz)) {
                return clazz;
            }
            //	Make sure that it is a PO class
            Class<?> superClazz = clazz.getSuperclass();
            //	Validate super class
            while (superClazz != null) {
                if (superClazz == DB_Support.class) {
                    Log.e("Error loading class", "Use: " + className);
                    return clazz;
                }
                //	Get Super Class
                superClazz = superClazz.getSuperclass();
            }
        } catch (Exception e) {
            Log.e("Loading class Error", e.getMessage());
        }
        //
        Log.e("Not found", "Class: " + className);
        return null;
    }	//	getHandlerClass

    /**
     * Load class for export
     * @throws Exception
     */
    private void loadClass() throws Exception {
        if(database != null) {
            return;
        }
        //	Load it
        //	Get class from parent
        Class<?> clazz = getHandlerClass();
        //	Not yet implemented
        if (clazz == null) {
            Log.d("Class not found", "Using Standard Class");
            database = null;
            throw new Exception("Class for connection not found");
        }
        //
        Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[]{Context.class});
        //	new instance
        database = (DB_Support) constructor.newInstance(new Object[] {getContext()});
    }

    /**
     * Helper Methods
     */
    public void save(PO entity) throws Exception {
        DB_Support database = getDatabase();
        //  Is Open
        boolean isOpen = database.isOpen();
        if(!isOpen) {
            database.open();
        }
        //  Save
        database.savePO(entity);
        if(!isOpen) {
            database.close();
        }
    }
}
