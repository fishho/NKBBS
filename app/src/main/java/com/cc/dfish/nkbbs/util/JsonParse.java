package com.cc.dfish.nkbbs.util;

/**
 * Created by dfish on 2016/2/3.
 */
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cc.dfish.nkbbs.bean.Topic;


public class JsonParse {
    public static List<Topic> parseGroupArticles(JSONObject resp) {
        List<Topic> mTopics = new ArrayList<Topic>();
        Topic topic = new Topic();
        JSONObject jsonData =  resp.getJSONObject("data");
        JSONArray jsonTopic = jsonData.getJSONArray("group_articles");
        mTopics = JSON.parseArray(jsonTopic.toJSONString(), Topic.class);
        return mTopics;

    }
}

//JSONObject jsonObject = new JSONObject();
//Topic topic = new Topic();
//for(int i = 0; i<jsonArray.length();i++){
//	try {
//		jsonObject = jsonArray.getJSONObject(i);
//	} catch (JSONException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	topic = new Topic();
//	try {
//		topic.setAuthor(jsonObject.getString("author"));
//		topic.setName(jsonObject.getString("name"));
//		topic.setGroupname(jsonObject.getString("groupname"));
//		topic.setReply_num(jsonObject.getString("reply_num"));
//		topic.setCreatime(jsonObject.getString("creatime"));
//		topic.setG_a_id(jsonObject.getString("g_a_id"));
//		newsBeanList.add(topic);
//	}
//	 catch (JSONException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//}