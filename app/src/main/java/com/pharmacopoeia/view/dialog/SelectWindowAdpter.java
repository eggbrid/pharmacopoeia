package com.pharmacopoeia.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.bean.model.SelectWindowModel;

import java.util.List;


/**
 * 提示框适配器
 * 
 * @author Administrator
 * 
 */
public class SelectWindowAdpter extends BaseAdapter {
	private Context context;
	public List<SelectWindowModel> selectWindowModels;

	// private HTGridViewEnum type;
	// private String spTypes;

	public List<SelectWindowModel> getModels() {
		return selectWindowModels;
	}

	public SelectWindowAdpter(Context context,
                              List<SelectWindowModel> selectWindowModels) {
		this.context = context;
		this.selectWindowModels = selectWindowModels;
	}

	// public MineChooseAreaAdpter(Context context, List<String> areas,
	// HTGridViewEnum type) {
	// this.context = context;
	// this.areas = areas;
	// this.type = type;
	// }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return selectWindowModels == null ? 0 : selectWindowModels.size();
	}

	@Override
	public SelectWindowModel getItem(int position) {
		// TODO Auto-generated method stub
		if (selectWindowModels != null && selectWindowModels.size() != 0) {
			return selectWindowModels.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// String model = getItem(position);
		ViewHolders holders = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.me_detail_pop_item_view, null);
			holders = new ViewHolders();
			holders.tv_value = (TextView) convertView
					.findViewById(R.id.tv_value);
			convertView.setTag(holders);
		} else {
			holders = (ViewHolders) convertView.getTag();
		}
		holders.tv_value.setText(selectWindowModels.get(position).getValue());

		return convertView;
	}

	class ViewHolders {
		TextView tv_value;
		// 时间 昵称 评论内容
	}
}