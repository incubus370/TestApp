package com.test.imagefilter.ui.filter.filterlist;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.test.imagefilter.R;
import com.test.imagefilter.filters.BlockFilter;
import com.test.imagefilter.filters.BlurFilter;
import com.test.imagefilter.filters.Filter;
import com.test.imagefilter.filters.GaussianBlurFilter;
import com.test.imagefilter.filters.GothamFilter;
import com.test.imagefilter.filters.GrayFilter;
import com.test.imagefilter.filters.HDRFilter;
import com.test.imagefilter.filters.InvertFilter;
import com.test.imagefilter.filters.LightFilter;
import com.test.imagefilter.filters.LomoFilter;
import com.test.imagefilter.filters.MotionBlurFilter;
import com.test.imagefilter.filters.NeonFilter;
import com.test.imagefilter.filters.OilFilter;
import com.test.imagefilter.filters.OldFilter;
import com.test.imagefilter.filters.PixelateFilter;
import com.test.imagefilter.filters.ReliefFilter;
import com.test.imagefilter.filters.SharpenFilter;
import com.test.imagefilter.filters.SketchFilter;
import com.test.imagefilter.filters.SoftGlowFilter;
import com.test.imagefilter.filters.TvFilter;
import com.test.imagefilter.ui.BaseFragment;
import com.test.imagefilter.utils.FragmentUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Image filter list fragment with previews
 */
public class SelectFilterFragment extends BaseFragment {

    @Arg
    String pathToPreviewImage;
    @Bind(R.id.filter_recycler_view)
    RecyclerView recyclerView;
    private FilterAdapter adapter;
    private FilterSelectionListener filterSelectionListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        filterSelectionListener = FragmentUtils.getParent(this, FilterSelectionListener.class);
        if (filterSelectionListener == null) {
            throw new IllegalArgumentException(
                "At least one fragment parent should implement FilterSelectionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_select_filter, container, false);
        ButterKnife.bind(this, root);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) root.getLayoutParams();
        lp.gravity = Gravity.BOTTOM;
        recyclerView.setLayoutManager(
            new WrapContentLinaerLayoutManger(getActivity(), LinearLayoutManager.HORIZONTAL,
                false));
        adapter = new FilterAdapter(
            new FilterViewHolder.ViewHolderProvider(getActivity(), filterSelectionListener));
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new GrayFilter());
        filterList.add(new OldFilter());
        filterList.add(new BlockFilter());
        filterList.add(new SketchFilter());
        filterList.add(new BlurFilter());
        filterList.add(new GaussianBlurFilter());
        filterList.add(new GothamFilter());
        filterList.add(new HDRFilter());
        filterList.add(new InvertFilter());
        filterList.add(new LightFilter());
        filterList.add(new LomoFilter());
        filterList.add(new MotionBlurFilter());
        filterList.add(new NeonFilter());
        filterList.add(new OilFilter());
        filterList.add(new PixelateFilter());
        filterList.add(new ReliefFilter());
        filterList.add(new SharpenFilter());
        filterList.add(new SoftGlowFilter());
        filterList.add(new TvFilter());
        adapter.setData(pathToPreviewImage, filterList);
        recyclerView.setAdapter(adapter);
        return root;
    }

    /**
     * Filter selection listener interface
     */
    public interface FilterSelectionListener {

        /**
         * Method called when filter selected
         */
        void onFilterSelected(Filter filter);
    }
}
