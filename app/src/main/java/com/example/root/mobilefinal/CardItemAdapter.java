package com.example.root.mobilefinal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.List;
import java.util.Map;

public class CardItemAdapter extends RecyclerView.Adapter {
    List<CartItem> cartItemList;
    Context context;
    Backend.Callback<Integer> onCartItemChangedCallback;

    public CardItemAdapter(Context context, List<CartItem> cartItemList, Backend.Callback<Integer> onCartItemChangedCallback) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.onCartItemChangedCallback = onCartItemChangedCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_mycart, parent, false);
        return new CardItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // TODO
        CardItemHolder cardItemHolder = (CardItemHolder) holder;
        cardItemHolder.bind(cartItemList.get(position));
        onCartItemChangedCallback.call(position);
        cardItemHolder.quantityNumber.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                cartItemList.get(position).quantity = String.valueOf(newValue);
                Backend.updateCartItemQuantity(cartItemList.get(position).iid, cartItemList.get(position).variation, String.valueOf(newValue), new Backend.Callback<Long>() {
                    @Override
                    public void call(Long data) {
                        if (data != -1L) cartItemList.get(position).quantity = data.toString();
                        if (data == 0L) {
                            cartItemList.remove(position);
                            notifyDataSetChanged();
                        }
                        onCartItemChangedCallback.call(position);
                    }
                });
            }
        });

        cardItemHolder.imageView_deleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Backend.removeCartItem(cartItemList.get(position).iid, cartItemList.get(position).variation, new Backend.Callback<Boolean>() {
                    @Override
                    public void call(Boolean data) {
                        cartItemList.remove(position);
                        notifyDataSetChanged();
                        onCartItemChangedCallback.call(-position);
                    }
                });
            }
        });

        cardItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemDetail.class);
                intent.putExtra("iid", cartItemList.get(position).iid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }
}
