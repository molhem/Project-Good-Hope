package com.goodhopes.poovam.projectgoodhopes.cardfragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.NetworkConnection;
import com.goodhopes.poovam.projectgoodhopes.interfaces.ResponseHandler;
import com.goodhopes.poovam.projectgoodhopes.parsers.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import link.fls.swipestack.SwipeStack;

/**
 * Created by poovam on 3/12/16.
 * fragment holding the card view
 * the first module of the app
 */

public class CardViewFragment extends Fragment {
    @BindView(R.id.swipeStack) SwipeStack swipeStack;
    ArrayList<String> a = new ArrayList<>();
    ArrayList<String> b = new ArrayList<>();
    SwipeStackAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final XMLParser parser = new XMLParser();

        String url ="http://www.dinakaran.com/rss_Latest.asp";
        //String url ="http://www.dinamani.com/%E0%AE%A4%E0%AE%B1%E0%AF%8D%E0%AE%AA%E0%AF%8B%E0%AE%A4%E0%AF%88%E0%AE%AF-%E0%AE%9A%E0%AF%86%E0%AE%AF%E0%AF%8D%E0%AE%A4%E0%AE%BF%E0%AE%95%E0%AE%B3%E0%AF%8D/%E0%AE%A4%E0%AE%B1%E0%AF%8D%E0%AE%AA%E0%AF%8B%E0%AE%A4%E0%AF%88%E0%AE%AF-%E0%AE%9A%E0%AF%86%E0%AE%AF%E0%AF%8D%E0%AE%A4%E0%AE%BF%E0%AE%95%E0%AE%B3%E0%AF%8D/rssfeed/?id=687&getXmlFeed=true";
        NetworkConnection.getInstance(getActivity().getApplicationContext()).getRSS(url,
                new ResponseHandler() {
                    @Override
                    public void parse(String response) {
                        try {
                            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Document doc = parser.getDomElement(response);
                        NodeList nl = doc.getElementsByTagName("item");
                        for (int i = 0; i < nl.getLength(); i++) {
                            Element e = (Element) nl.item(i);
                            a.add(parser.getValue(e,"title"));
                            Element line = (Element) e.getElementsByTagName("description").item(0);
                            b.add(parser.getCharacterDataFromElement(line));
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View cardView = inflater.inflate(R.layout.card_view_fragment,container,false);
        ButterKnife.bind(this,cardView);
        adapter =  new SwipeStackAdapter(a,b);
        swipeStack.setAdapter(adapter);
        return cardView;
    }
}
