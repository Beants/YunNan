package com.pilab.yunnan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pilab.yunnan.data.Sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import static android.support.constraint.Constraints.TAG;


public class HomeFragment extends Fragment {
    private DrawerLayout drawerLayout;
    private Traditional[] traditionals = {
            new Traditional("云南菜", R.drawable.ic_launcher_foreground, "滇菜即云南菜，是中国菜体系中民族特色最为突出的一个地方性菜系。"),
            new Traditional("西南官话", R.drawable.ic_launcher_foreground, "西南官话，亦称上江官话，学术上称为西南方言，属于现代汉语北方方言（另外六个方言是吴方言、湘方言、赣方言、客家方言、闽方言、粤方言）的四个次方言之一，即西南方言。"),
            new Traditional("香格里拉", R.drawable.ic_launcher_foreground, "香格里拉县是云南省国土面积最大、人口密度最低的县份之一，由于这一大一小，处子般的沟箐和山峰就比比皆是。在这些人迹罕至的地方珍藏着许多大自然的秘密。"),
            new Traditional("剑川", R.drawable.ic_launcher_foreground, "剑川县位于云南省西北部，大理白族自治州北部，地处大理、丽江和“香格里拉”中甸三大旅游热区之间，是通往“三江流”旅游区的重要通道。剑川地处东经99°33′—100°33′，北纬26°12′—26°41′。南距州府下关126公里，昆明471公里；北距丽江75公里，中甸185公里，总面积2250平方公里，县城金华镇海拔2200米，气温属南温带季风型，干湿分明，年平均雨量为739毫米，年平均气温12.3℃，冬无严寒，夏无酷暑。境内有白、汉、回、彝、傈僳、纳西等民族，其中白族占总人口的92％，是全国白族人口比例最高的县，是电影《五朵金花》中阿鹏的故乡，是文化部命名的\"中国木雕艺术之乡\"。"),
            new Traditional("澄江古生物化石群", R.drawable.ic_launcher_foreground, "澄江生物群为寒武纪早期，埋藏着距今5.3亿年的澄江生物化石群，帽天山化石带，呈带状蜿蜒分布，这条分布带长20公里，宽4.5公里，埋藏深度在50米以上。 2012年7月经过第36届世界遗产委员会投票表决，认定中国“澄江化石地”是地球生命演化的杰出范例，符合世界自然遗产标准，正式列入世界自然遗产名录。这是中国唯一的化石类世界自然遗产。"),
            new Traditional("云南石林", R.drawable.ic_launcher_foreground, "云南石林（路南石林）位于中国云南省石林彝族自治县，1982年11月8日经中华人民共和国国务院批准为首批国家重点风景名胜区，其时法定名称为“路南石林风景名胜区”。唯路南彝族自治县已于1998年10月8日经国务院批准同意更名为石林彝族自治县，因此现在“路南石林”名称多已不用，是否获国务院批准则不得而知。"),
    };
    private List<Traditional> traditionalList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        intiTraditional(view);
        Log.i(TAG, "onCreateView: " + traditionalList.toArray().toString());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        int spanCount = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        TraditionalAdapter adapter = new TraditionalAdapter(traditionalList);

        recyclerView.setAdapter(adapter);
        return view;
    }

    private void intiTraditional(View view) {
        traditionalList.clear();
        traditionalList.addAll(Arrays.asList(traditionals));
//        for (int i = 0; i < 10; i++) {
//            Random random = new Random();
//            int index = random.nextInt(traditionals.length);
//            traditionalList.add(traditionals[index]);
//
//        }


    }


}
