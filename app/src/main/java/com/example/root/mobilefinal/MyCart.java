package com.example.root.mobilefinal;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class MyCart extends AppCompatActivity {

    RecyclerView rv_cartItem;
    TextView textView_totalPrice;
    Button btn_order;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        initViews();
        setCartItem();
    }

    void initViews(){
        textView_totalPrice = findViewById(R.id.textView_totalPrice);
        rv_cartItem = findViewById(R.id.rv_cartItem);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getTotalPrice(List<CartItem> cartList, final Backend.Callback<Long> cb) {
        List<String> iids = cartList.stream().map((CartItem cartItem) -> cartItem.iid).collect(Collectors.toList());
        Map<String, CartItem> cartItemMap = cartList.stream().collect(Collectors.toMap(cart -> cart.iid, cart -> cart));
        for (String iid: iids) {
            Log.d("btag/getTotalPrice", "iid " + iid);
        }
        Backend.getMultiItems(iids, data -> {
            Long total = 0l;
            for (String iid: data.keySet()) {
                total += Long.valueOf(data.get(iid).price) * Long.valueOf(cartItemMap.get(iid).quantity);
            }
            cb.call(total);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setCartItem() {
        // TODO
        Backend.getCart(data -> {
            for (CartItem item: data) {
                Log.d("btag/getCart", "data " + item.iid);
            }
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rv_cartItem.setLayoutManager(linearLayoutManager);

            Backend.Callback<Integer> onCartItemUpdated = pos -> {
                Log.d("btag/onCartUpt", "ecec");
                getTotalPrice(data, data1 -> textView_totalPrice.setText(data1.toString() + " đ"));
            };

            rv_cartItem.setAdapter(new CardItemAdapter(getApplicationContext(), data, onCartItemUpdated));
        });
    }

}
