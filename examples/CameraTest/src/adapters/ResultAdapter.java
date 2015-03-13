package adapters;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import net.sourceforge.zbar.android.CameraTest.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

public class ResultAdapter extends BaseAdapter {

	private static FileOutputStream fos;

	private Bitmap bmp;
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;

	HashMap<String, String> resultp = new HashMap<String, String>();

	public ResultAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public String getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		TextView cost, type, harm, name, eltype, ingredients;

		ImageView image;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.listitem, parent, false);
		// Get the position
		resultp = data.get(position);
		name = (TextView) itemView.findViewById(R.id.pname);
		eltype = (TextView) itemView.findViewById(R.id.eltype);
		cost = (TextView) itemView.findViewById(R.id.cost);
		type = (TextView) itemView.findViewById(R.id.type);
		ingredients = (TextView) itemView.findViewById(R.id.ingredients);
		harm = (TextView) itemView.findViewById(R.id.harm);
		image = (ImageView) itemView.findViewById(R.id.prodimage);
		name.setText(resultp.get("pname"));
		ingredients.setText(resultp.get("ingredients"));
		eltype.setText(resultp.get("producttype"));
		cost.setText(resultp.get("price"));
		type.setText(resultp.get("type"));
		harm.setText(resultp.get("harmful_q"));

		Ion.with(image).load(resultp.get("images"));
		return itemView;

	}

}
