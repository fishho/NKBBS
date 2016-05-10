package com.cfish.rvb.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cfish.rvb.bean.Article;
import com.cfish.rvb.bean.Details;
import com.cfish.rvb.bean.Group;
import com.cfish.rvb.bean.Message;
import com.cfish.rvb.bean.MsgData;
import com.cfish.rvb.bean.Reply;
import com.cfish.rvb.bean.Topic;
import com.cfish.rvb.bean.User;


public class JsonParse {
	public static List<Topic> parseGroupArticles(JSONObject resp) {
		List<Topic> mTopics = new ArrayList<Topic>();
		Topic topic = new Topic();
		JSONObject jsonData =  resp.getJSONObject("data");
		JSONArray jsonTopic = jsonData.getJSONArray("group_articles");
		mTopics = JSON.parseArray(jsonTopic.toJSONString(), Topic.class);
		return mTopics;
		
	}

	public static List<Group> parseGoupList(JSONObject resp) {
		List<Group> mGroups =  new ArrayList<>();
		Group group =  new Group();
		JSONObject jsonData = resp.getJSONObject("data");
		JSONArray jsonGroup = jsonData.getJSONArray("groups");
		mGroups = JSON.parseArray(jsonGroup.toJSONString(), Group.class);
		return mGroups;
	}

	public static List<Reply> parseReplys(JSONObject resp) {
		List<Reply> mReplys =  new ArrayList<>();
		Reply reply =  new Reply();
		JSONObject jsonData = resp.getJSONObject("data");
		JSONArray jsonReply= jsonData.getJSONArray("reply");
        mReplys = JSON.parseArray(jsonReply.toJSONString(), Reply.class);
		return mReplys;
	}

	public static Article parseArticle(JSONObject resp) {
		JSONObject jsonData = resp.getJSONObject("data");
		Article article = new Article();
		JSONObject jsonArticle = jsonData.getJSONObject("article");
		article = JSON.parseObject(jsonArticle.toJSONString(), Article.class);
		return  article;
	}

	public static Details parseDetails(JSONObject resp) {
        JSONObject jsonData = resp.getJSONObject("data");
        Details details = JSON.parseObject(jsonData.toJSONString(),Details.class);
        return details;
    }

	public static User parseUser(JSONObject resp) {
		User user = new User();
		JSONObject jsonData = resp.getJSONObject("data");
		user = JSON.parseObject(jsonData.toJSONString(),User.class);
		return  user;
	}

	public static MsgData parseMsg(JSONObject resp) {
        MsgData msgData;
        JSONObject jsonData = resp.getJSONObject("data");
        msgData = JSON.parseObject(jsonData.toJSONString(),MsgData.class);
        return msgData;
    }

    public static Message parseNotice(String resp) {
        Message notice = JSON.parseObject(resp,Message.class);
        return notice;
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