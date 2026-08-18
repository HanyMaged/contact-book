package com.example.contactbook;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    private Cursor cursor;

    public ContactsAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        cursor.moveToPosition(position);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsOpenHelper.COLUMN_NAME));
        String phone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsOpenHelper.COLUMN_PHONE));

        holder.textViewName.setText(name);
        holder.textViewPhone.setText(phone);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewPhone;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
        }
    }
}