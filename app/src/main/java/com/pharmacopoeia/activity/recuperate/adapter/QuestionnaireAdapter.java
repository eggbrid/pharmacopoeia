package com.pharmacopoeia.activity.recuperate.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.recuperate.SymptomResultActivity;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.base.PBaseAdapter;
import com.pharmacopoeia.base.PBaseHoTagAdapter;
import com.pharmacopoeia.bean.model.QuestionnaireModel;
import com.pharmacopoeia.interfaces.enums.QuestType;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.view.CustomListView;

import java.util.List;

/**
 * Created by xus on 2017/8/30.
 */

public class QuestionnaireAdapter extends PBaseHoTagAdapter<QuestionnaireModel, QuestionnaireAdapter.ViewHolder> {
    private OnFinish onFinish;
    private OnMainQuestSelect onMainQuestSelect;

    public OnMainQuestSelect getOnMainQuestSelect() {
        return onMainQuestSelect;
    }

    public void setOnMainQuestSelect(OnMainQuestSelect onMainQuestSelect) {
        this.onMainQuestSelect = onMainQuestSelect;
    }

    public OnFinish getOnFinish() {
        return onFinish;
    }

    public void setOnFinish(OnFinish onFinish) {
        this.onFinish = onFinish;
    }

    public QuestionnaireAdapter(Context context, List<QuestionnaireModel> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, final ViewHolder viewHolder) {
        final QuestionnaireModel questionnaireModel = list.get(i);
        switch (QuestType.getEnum(questionnaireModel.getType())) {
            case CHECKBOXQ1:
                CompoundButton.OnCheckedChangeListener onCheckedChangeListener1 = new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String value = "";
                        value = viewHolder.cq1.isChecked() ? questionnaireModel.getV1() : "";
                        questionnaireModel.setValue(value);
                        setIsMain(questionnaireModel);

                        setFinish();


                    }
                };

                viewHolder.cq1.setText(questionnaireModel.getQ1());
                if (!TextUtils.isEmpty(questionnaireModel.getValue())) {
                    viewHolder.cq1.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV1()));
                }


                viewHolder.cq1.setOnCheckedChangeListener(onCheckedChangeListener1);
                viewHolder.title.setText(questionnaireModel.getTitle());

                break;
            case CHECKBOXQ2:
                CompoundButton.OnCheckedChangeListener onCheckedChangeListener2 = new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String value = "";
                        value = viewHolder.cq1.isChecked() ? questionnaireModel.getV1() : "";
                        if (!TextUtils.isEmpty(value)) {
                            value = value + ",";
                        }
                        value = value + (viewHolder.cq2.isChecked() ? questionnaireModel.getV2() : "");
                        questionnaireModel.setValue(value);
                        setIsMain(questionnaireModel);

                        setFinish();


                    }
                };

                viewHolder.cq1.setText(questionnaireModel.getQ1());
                viewHolder.cq2.setText(questionnaireModel.getQ2());
                if (!TextUtils.isEmpty(questionnaireModel.getValue())) {
                    viewHolder.cq1.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV1()));
                    viewHolder.cq2.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV2()));
                }


                viewHolder.cq1.setOnCheckedChangeListener(onCheckedChangeListener2);
                viewHolder.cq2.setOnCheckedChangeListener(onCheckedChangeListener2);
                viewHolder.title.setText(questionnaireModel.getTitle());

                break;
            case CHECKBOXQ3:
                CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String value = "";
                        value = viewHolder.cq1.isChecked() ? questionnaireModel.getV1() : "";
                        if (!TextUtils.isEmpty(value)) {
                            value = value + ",";
                        }
                        value = value + (viewHolder.cq2.isChecked() ? questionnaireModel.getV2() : "");
                        if (!TextUtils.isEmpty(value)) {
                            value = value + ",";
                        }
                        value = value + (viewHolder.cq3.isChecked() ? questionnaireModel.getV3() : "");
                        questionnaireModel.setValue(value);
                        setIsMain(questionnaireModel);

                        setFinish();


                    }
                };

                viewHolder.cq1.setText(questionnaireModel.getQ1());
                viewHolder.cq2.setText(questionnaireModel.getQ2());
                viewHolder.cq3.setText(questionnaireModel.getQ3());
                if (!TextUtils.isEmpty(questionnaireModel.getValue())) {
                    viewHolder.cq1.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV1()));
                    viewHolder.cq2.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV2()));
                    viewHolder.cq3.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV3()));
                }


                viewHolder.cq1.setOnCheckedChangeListener(onCheckedChangeListener);
                viewHolder.cq2.setOnCheckedChangeListener(onCheckedChangeListener);
                viewHolder.cq3.setOnCheckedChangeListener(onCheckedChangeListener);
                viewHolder.title.setText(questionnaireModel.getTitle());

                break;
            case CHECKBOXQ4:
                viewHolder.cq1.setText(questionnaireModel.getQ1());
                viewHolder.cq2.setText(questionnaireModel.getQ2());
                viewHolder.cq3.setText(questionnaireModel.getQ3());
                viewHolder.cq4.setText(questionnaireModel.getQ4());
                if (!TextUtils.isEmpty(questionnaireModel.getValue())) {
                    viewHolder.cq1.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV1()));
                    viewHolder.cq2.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV2()));
                    viewHolder.cq3.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV3()));
                    viewHolder.cq4.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV4()));
                }

                viewHolder.title.setText(questionnaireModel.getTitle());
                CompoundButton.OnCheckedChangeListener onCheckedChangeListener4 = new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String value = "";
                        value = viewHolder.cq1.isChecked() ? questionnaireModel.getV1() : "";
                        if (!TextUtils.isEmpty(value)) {
                            value = value + ",";
                        }
                        value = value + (viewHolder.cq2.isChecked() ? questionnaireModel.getV2() : "");
                        if (!TextUtils.isEmpty(value)) {
                            value = value + ",";
                        }
                        value = value + (viewHolder.cq3.isChecked() ? questionnaireModel.getV3() : "");
                        questionnaireModel.setValue(value);
                        if (!TextUtils.isEmpty(value)) {
                            value = value + ",";
                        }
                        value = value + (viewHolder.cq4.isChecked() ? questionnaireModel.getV4() : "");
                        questionnaireModel.setValue(value);
                        setIsMain(questionnaireModel);

                        setFinish();
                    }
                };
                viewHolder.cq1.setOnCheckedChangeListener(onCheckedChangeListener4);
                viewHolder.cq2.setOnCheckedChangeListener(onCheckedChangeListener4);
                viewHolder.cq3.setOnCheckedChangeListener(onCheckedChangeListener4);
                viewHolder.cq4.setOnCheckedChangeListener(onCheckedChangeListener4);

                break;

            case CHECKBOXQ5:
                viewHolder.title.setText(questionnaireModel.getTitle());
                CItemadapter cItemadapter = new CItemadapter(context, questionnaireModel.getQm(), new CItemadapter.OnValueChange() {
                    @Override
                    public void onChange(List<Boolean> values) {
                        String value = "";

                        for (int i=0;i<values.size();i++){
                            if (values.get(i)){
                                value = questionnaireModel.getQv().get(i) + ",";
                            }
                        }
                        if (!TextUtils.isEmpty(value)){
                            value=value.substring(0,value.length()-1);
                        }
                        questionnaireModel.setValue(value);
                        setIsMain(questionnaireModel);
                        setFinish();
                    }
                },questionnaireModel.getValue(),questionnaireModel.getQv());
                viewHolder.list.setAdapter(cItemadapter);
                cItemadapter.notifyDataSetChanged();
                break;
            case RADIOQ1:
                viewHolder.rq1.setText(questionnaireModel.getQ1());
                if (!TextUtils.isEmpty(questionnaireModel.getValue())) {
                    viewHolder.rq1.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV1()));
                }

                viewHolder.title.setText(questionnaireModel.getTitle());
                viewHolder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        switch (checkedId) {
                            case R.id.q1:
                                questionnaireModel.setValue(questionnaireModel.getV1());

                                break;
                        }
                        setIsMain(questionnaireModel);

                        setFinish();

                    }
                });
                break;
            case RADIOQ2:
                viewHolder.rq1.setText(questionnaireModel.getQ1());
                viewHolder.rq2.setText(questionnaireModel.getQ2());
                if (!TextUtils.isEmpty(questionnaireModel.getValue())) {
                    viewHolder.rq1.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV1()));
                    viewHolder.rq2.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV2()));
                }

                viewHolder.title.setText(questionnaireModel.getTitle());
                viewHolder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        switch (checkedId) {
                            case R.id.q1:
                                questionnaireModel.setValue(questionnaireModel.getV1());

                                break;
                            case R.id.q2:
                                questionnaireModel.setValue(questionnaireModel.getV2());

                                break;
                        }
                        setIsMain(questionnaireModel);

                        setFinish();

                    }
                });
                break;
            case RADIOQ3:
                viewHolder.rq1.setText(questionnaireModel.getQ1());
                viewHolder.rq2.setText(questionnaireModel.getQ2());
                viewHolder.rq3.setText(questionnaireModel.getQ3());
                if (!TextUtils.isEmpty(questionnaireModel.getValue())) {
                    viewHolder.rq1.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV1()));
                    viewHolder.rq2.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV2()));
                    viewHolder.rq3.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV3()));
                }

                viewHolder.title.setText(questionnaireModel.getTitle());
                viewHolder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        switch (checkedId) {
                            case R.id.q1:
                                questionnaireModel.setValue(questionnaireModel.getV1());

                                break;
                            case R.id.q2:
                                questionnaireModel.setValue(questionnaireModel.getV2());

                                break;
                            case R.id.q3:
                                questionnaireModel.setValue(questionnaireModel.getV3());

                                break;
                        }
                        setIsMain(questionnaireModel);

                        setFinish();

                    }
                });
                break;
            case RADIOQ4:
                viewHolder.rq1.setText(questionnaireModel.getQ1());
                viewHolder.rq2.setText(questionnaireModel.getQ2());
                viewHolder.rq3.setText(questionnaireModel.getQ3());
                viewHolder.rq4.setText(questionnaireModel.getQ4());
                if (!TextUtils.isEmpty(questionnaireModel.getValue())) {
                    viewHolder.rq1.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV1()));
                    viewHolder.rq2.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV2()));
                    viewHolder.rq3.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV3()));
                    viewHolder.rq4.setChecked(questionnaireModel.getValue().contains(questionnaireModel.getV4()));

                }
                viewHolder.title.setText(questionnaireModel.getTitle());
                viewHolder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        switch (checkedId) {
                            case R.id.q1:
                                questionnaireModel.setValue(questionnaireModel.getV1());

                                break;
                            case R.id.q2:
                                questionnaireModel.setValue(questionnaireModel.getV2());

                                break;
                            case R.id.q3:
                                questionnaireModel.setValue(questionnaireModel.getV3());

                                break;
                            case R.id.q4:
                                questionnaireModel.setValue(questionnaireModel.getV4());

                                break;
                        }
                        setIsMain(questionnaireModel);

                        setFinish();
                    }
                });
                break;

            case RADIOQ5:
                viewHolder.title.setText(questionnaireModel.getTitle());
                RItemadapter rItemadapter = new RItemadapter(context, questionnaireModel.getQm(), new RItemadapter.OnValueChange() {
                    @Override
                    public void onChange(int i) {
                        questionnaireModel.setValue(questionnaireModel.getQv().get(i));
                        setIsMain(questionnaireModel);
                        setFinish();
                    }
                },questionnaireModel.getValue(),questionnaireModel.getQv());
                viewHolder.list.setAdapter(rItemadapter);
                rItemadapter.notifyDataSetChanged();
                break;
        }
        return view;
    }

    @Override
    public ViewHolder setViewHolder(View view, int pos) {
        QuestionnaireModel questionnaireModel = list.get(pos);
        return new ViewHolder(view, QuestType.getEnum(questionnaireModel.getType()));
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        QuestionnaireModel questionnaireModel = list.get(i);
        return inflater.inflate(QuestType.getEnum(questionnaireModel.getType()).getLayout(), viewGroup, false);
    }

    public class ViewHolder extends BaseViewHolder {
        private CheckBox cq1, cq2, cq3, cq4;
        private RadioButton rq1, rq2, rq3, rq4;
        private RadioGroup rg;
        private TextView title;
        private QuestType questType;

        private CustomListView list;

        public ViewHolder(View root) {
            super(root);
        }

        public ViewHolder(View root, QuestType questType) {
            super();
            this.questType = questType;
            initView(root);
        }

        @Override
        public void initView(View root) {
            switch (questType) {
                case CHECKBOXQ1:
                    rg = (RadioGroup) root.findViewById(R.id.rg);
                    title = (TextView) root.findViewById(R.id.title);
                    cq1 = (CheckBox) root.findViewById(R.id.q1);
                    break;
                case CHECKBOXQ2:
                    rg = (RadioGroup) root.findViewById(R.id.rg);
                    title = (TextView) root.findViewById(R.id.title);
                    cq1 = (CheckBox) root.findViewById(R.id.q1);
                    cq2 = (CheckBox) root.findViewById(R.id.q2);

                    break;
                case CHECKBOXQ3:
                    rg = (RadioGroup) root.findViewById(R.id.rg);
                    title = (TextView) root.findViewById(R.id.title);
                    cq1 = (CheckBox) root.findViewById(R.id.q1);
                    cq2 = (CheckBox) root.findViewById(R.id.q2);
                    cq3 = (CheckBox) root.findViewById(R.id.q3);
                    break;
                case CHECKBOXQ4:
                    rg = (RadioGroup) root.findViewById(R.id.rg);
                    title = (TextView) root.findViewById(R.id.title);
                    cq1 = (CheckBox) root.findViewById(R.id.q1);
                    cq2 = (CheckBox) root.findViewById(R.id.q2);
                    cq3 = (CheckBox) root.findViewById(R.id.q3);
                    cq4 = (CheckBox) root.findViewById(R.id.q4);
                    break;
                case CHECKBOXQ5:
                    title = (TextView) root.findViewById(R.id.title);
                    list = (CustomListView) root.findViewById(R.id.list);
                    break;
                case RADIOQ1:
                    rg = (RadioGroup) root.findViewById(R.id.rg);
                    title = (TextView) root.findViewById(R.id.title);
                    rq1 = (RadioButton) root.findViewById(R.id.q1);
                    break;
                case RADIOQ2:
                    rg = (RadioGroup) root.findViewById(R.id.rg);
                    title = (TextView) root.findViewById(R.id.title);
                    rq1 = (RadioButton) root.findViewById(R.id.q1);
                    rq2 = (RadioButton) root.findViewById(R.id.q2);
                    break;
                case RADIOQ3:
                    rg = (RadioGroup) root.findViewById(R.id.rg);
                    title = (TextView) root.findViewById(R.id.title);
                    rq1 = (RadioButton) root.findViewById(R.id.q1);
                    rq2 = (RadioButton) root.findViewById(R.id.q2);
                    rq3 = (RadioButton) root.findViewById(R.id.q3);
                    break;
                case RADIOQ4:
                    rg = (RadioGroup) root.findViewById(R.id.rg);
                    title = (TextView) root.findViewById(R.id.title);
                    rq1 = (RadioButton) root.findViewById(R.id.q1);
                    rq2 = (RadioButton) root.findViewById(R.id.q2);
                    rq3 = (RadioButton) root.findViewById(R.id.q3);
                    rq4 = (RadioButton) root.findViewById(R.id.q4);
                    break;
                case RADIOQ5:
                    title = (TextView) root.findViewById(R.id.title);
                    list = (CustomListView) root.findViewById(R.id.list);
                    break;
            }

        }
    }

    public void setIsMain(QuestionnaireModel questionnaireModel) {
        if (questionnaireModel.isMainQuest()) {
            onMainQuestSelect.onMainQuestSelect(questionnaireModel.getValue());
        }
    }

    public void setFinish() {

        onFinish.onFinish(isFinish());
//
//        boolean hasFinish = hasFinish();
//        if (isFinish()) {
//            if (hasFinish) {
//                return;
//            } else {
//                QuestionnaireModel questionnaireModel = new QuestionnaireModel();
//                questionnaireModel.setType(QuestType.FINISH.getType());
//                list.add(questionnaireModel);
//                notifyDataSetChanged();
//            }
//        } else {
//            if (hasFinish) {
//                list.remove(list.size() - 1);
//                notifyDataSetChanged();
//            }
//        }
    }

    public boolean isFinish() {
        for (QuestionnaireModel model : list) {
            if (TextUtils.isEmpty(model.getValue())) {
                return false;
            }
        }
        return true;
    }

    public boolean hasFinish() {
        for (QuestionnaireModel model : list) {
            if (model.getType().equals(QuestType.FINISH.getType())) {
                return true;
            }
        }
        return false;
    }

    public interface OnFinish {
        void onFinish(boolean isFinish);
    }

    public interface OnMainQuestSelect {
        void onMainQuestSelect(String values);
    }
}
