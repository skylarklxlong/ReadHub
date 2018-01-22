package online.himakeit.readhub.utils;

import java.util.HashMap;
import java.util.Map;

import online.himakeit.readhub.ui.fragment.BaseFragment;

/**
 * @author：LiXueLong
 * @date：2018/1/20
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class FragmentUtil {
    private static Map<String, BaseFragment> fragmentMap = new HashMap<>();

    public static BaseFragment createFragment(Class<?> clazz) {
        return createFragment(clazz, true);
    }

    public static BaseFragment createFragment(Class<?> clazz, boolean isObtain) {
        BaseFragment resultFragment = null;
        String className = clazz.getName();
        if (fragmentMap.containsKey(className)) {
            resultFragment = fragmentMap.get(className);
        } else {
            try {
                try {
                    resultFragment = (BaseFragment) Class.forName(className).newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (isObtain) {
                fragmentMap.put(className, resultFragment);
            }
        }

        return resultFragment;
    }
}
