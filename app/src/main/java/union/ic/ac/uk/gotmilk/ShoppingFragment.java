package union.ic.ac.uk.gotmilk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import behaviour.union.ic.ac.uk.gotmilk.R;

/**
 * Created by leszek on 1/27/18.
 */

public class ShoppingFragment extends Fragment {
    private RecyclerView mShoppingRecycleView;
    private ShoppingFragment.ShoppingAdapter mAdapter;
    private LinearLayoutManager linearLayout;
    private List<ShopItem> shopItems;
    private Shopping shopping;
    private int shoppingIndex;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.shopping_list_fragment, container, false);

        
        ShoppingLab shoppingLab = ShoppingLab.get(getContext());
        ArrayList<Shopping> shoppingArr = shoppingLab.getShoppingArr();
        
        if (getArguments().getBoolean("new")) { //set next fragment
            //if the shopping is new the new list has the name "shopping" + size of array +1
            shopping = new Shopping("shopping " + String.valueOf(shoppingArr.size()+1));
            shoppingArr.add(shopping);
        } else {
            shoppingIndex = getArguments().getInt("index");
            shopping = shoppingArr.get(shoppingIndex);
        }
        shopItems = shopping.getShopItems();


        mShoppingRecycleView = view.findViewById(R.id.shopping_list_recycler_view);
        linearLayout = new LinearLayoutManager(getContext());
        mShoppingRecycleView.setLayoutManager(linearLayout);



        updateUI();

        return view;
    }

    private void updateUI() {
        mAdapter = new ShoppingFragment.ShoppingAdapter(shopItems);
        mShoppingRecycleView.setAdapter(mAdapter);
    }

    private class ShoppingHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mSize;
        public Shopping mShopping;


        public ShoppingHolder(View itemView) {
            super(itemView);

//            itemView.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    Fragment fragment = new ShoppingDisplayFragment();
//                    Bundle args = new Bundle();
//                    args.putBoolean("teamList", true);
//                    fragment.setArguments(args);
//                    ((MainActivity) getActivity()).setCurrentShoppingShown(mShopping);
//                    fragment.setArguments(args);
//                    getFragmentManager().beginTransaction().
//                            replace(R.id.flContent, fragment).addToBackStack(null).commit();
//                }
//            });

            mName = (TextView) itemView.findViewById(R.id.list_shopping_name);
            mSize = (TextView) itemView.findViewById(R.id.list_shopping_size);
        }
    }

    private class ShoppingAdapter extends RecyclerView.Adapter<ShoppingFragment.ShoppingHolder> {
        private List<ShopItem> mShopItems;
        public ShoppingAdapter(List<ShopItem> shopItems) {
            mShopItems = shopItems;
        }

        @Override
        public ShoppingFragment.ShoppingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.shopping_list_row, parent, false);
            return new ShoppingFragment.ShoppingHolder(view);
        }
        @Override
        public void onBindViewHolder(ShoppingFragment.ShoppingHolder holder, int position) {
            ShopItem shopItem= mShopItems.get(position);
            holder.mShopping = shopping;
            holder.mName.setText(shopping.getName());
            holder.mSize.setText(shopping.getSize());
        }

        @Override
        public int getItemCount() {
            return mShopItems.size();
        }

    }
}
