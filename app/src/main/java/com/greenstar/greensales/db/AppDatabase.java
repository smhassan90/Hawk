package com.greenstar.greensales.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.greenstar.greensales.dao.DashboardDAO;
import com.greenstar.greensales.dao.GSSWorkWithDAO;
import com.greenstar.greensales.dao.LeaveEntryDAO;
import com.greenstar.greensales.dao.OrderDAO;
import com.greenstar.greensales.dao.ProductDAO;
import com.greenstar.greensales.dao.ProductOrderDAO;
import com.greenstar.greensales.dao.SDCustomerDAO;
import com.greenstar.greensales.dao.SDDepotDAO;
import com.greenstar.greensales.dao.SDDepotStaffDAO;
import com.greenstar.greensales.dao.SDSKUGroupDAO;
import com.greenstar.greensales.dao.SDStaffDAO;
import com.greenstar.greensales.dao.SDStatusDAO;
import com.greenstar.greensales.dao.SDTownCustomerDAO;
import com.greenstar.greensales.dao.SDTownDAO;
import com.greenstar.greensales.dao.SDTownDepotDAO;
import com.greenstar.greensales.dao.SDTownStaffDAO;
import com.greenstar.greensales.dao.UnapprovedSDCustomerDAO;
import com.greenstar.greensales.model.Dashboard;
import com.greenstar.greensales.model.GSSWorkWith;
import com.greenstar.greensales.model.LeaveEntry;
import com.greenstar.greensales.model.Orderr;
import com.greenstar.greensales.model.Product;
import com.greenstar.greensales.model.ProductOrder;
import com.greenstar.greensales.model.SDCustomer;
import com.greenstar.greensales.model.SDDepot;
import com.greenstar.greensales.model.SDDepotStaff;
import com.greenstar.greensales.model.SDSKUGroup;
import com.greenstar.greensales.model.SDStaff;
import com.greenstar.greensales.model.SDStatus;
import com.greenstar.greensales.model.SDTown;
import com.greenstar.greensales.model.SDTownCustomer;
import com.greenstar.greensales.model.SDTownDepot;
import com.greenstar.greensales.model.SDTownStaff;
import com.greenstar.greensales.model.UnapprovedSDCustomer;

@Database(entities = {LeaveEntry.class, Dashboard.class, GSSWorkWith.class, UnapprovedSDCustomer.class, ProductOrder.class, Orderr.class, Product.class, SDSKUGroup.class, SDStatus.class, SDCustomer.class, SDDepot.class, SDDepotStaff.class, SDStaff.class, SDTown.class, SDTownCustomer.class, SDTownDepot.class, SDTownStaff.class},
        version = 4)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "salesdb";
    private static AppDatabase INSTANCE;

    public abstract SDDepotDAO getDepotDAO();
    public abstract SDDepotStaffDAO getDepotStaffDAO();
    public abstract SDStaffDAO getStaffDAO();
    public abstract SDTownCustomerDAO getTownCustomerDAO();
    public abstract SDTownDAO getTownDAO();
    public abstract SDTownDepotDAO getTownDepotDAO();
    public abstract SDTownStaffDAO getTownStaffDAO();
    public abstract SDCustomerDAO getCustomerDAO();
    public abstract SDStatusDAO getStatusDAO();
    public abstract SDSKUGroupDAO getSdskuGroupDAO();
    public abstract OrderDAO getOrderDAO();
    public abstract ProductDAO getProductDAO();
    public abstract ProductOrderDAO getProductOderDAO();
    public abstract UnapprovedSDCustomerDAO getUnapprovedSDCustomerDAO();
    public abstract GSSWorkWithDAO getWorkWithDAO();
    public abstract LeaveEntryDAO getLeaveEntryDAO();
    public abstract DashboardDAO getDashboardDAO();
    public static AppDatabase getAppDatabase(Context context) {

        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
