package com.example.chadi.orderfood.Databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.chadi.orderfood.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="EatItDB.db";
    private static final int DB_VER=1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order>getCarts(){
        SQLiteDatabase db=getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String[] sqlSelect={"productName","productId","quantity","price","discount"};
        String sqlTable="OrderDetail";
        qb.setTables(sqlTable);
        Cursor c=qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order>result=new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new Order(c.getString(c.getColumnIndex("productId")),
                                    c.getString(c.getColumnIndex("productName")),
                                    c.getString(c.getColumnIndex("quantity")),
                                    c.getString(c.getColumnIndex("discount")),
                                    c.getString(c.getColumnIndex("price"))
                                    ));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order){

        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("INSRET INTO  OrderDetail (productId,productName,quantity,price,discount) values('%s','%s','%s','%s','%s');"
        ,order.getProductId(),
         order.getProductName(),
         order.getQuantity(),
         order.getPrice(),
         order.getDiscount());
        db.execSQL(query);
    }

    public void cleanCart() {

        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }


}
