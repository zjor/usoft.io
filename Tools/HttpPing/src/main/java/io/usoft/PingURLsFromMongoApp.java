package io.usoft;

import com.mongodb.*;

import java.util.*;

public class PingURLsFromMongoApp {

	private static final String[] IMAGE_TYPES = new String[]{
			"android-small",
			"android-big",
			"ios-small",
			"ios-big"
	};

	private static final String IMAGE_TYPE_KEYWORD = "${image.type}";

	public static void main(String[] args) throws Exception {
		MongoClient mongoClient = new MongoClient(new ServerAddress("127.0.0.1", 27017));
		DB db = mongoClient.getDB("insta_categories");
		DBCollection collection = db.getCollection("transaction_categories");

		DBCursor cursor = collection.find(null,
				new BasicDBObject("colored_icon", 1)
						.append("icon", 1)
						.append("_id", 0));

		List<String> urls = new LinkedList<String>();

		while (cursor.hasNext()) {
			DBObject next = cursor.next();
			urls.add("" + next.get("colored_icon"));
			urls.add("" + next.get("icon"));
		}

		cursor.close();

		Set<String> expandedUrls = new TreeSet<String>();
		for (String url: urls) {
			for (String type: IMAGE_TYPES) {
				expandedUrls.add(url.replace(IMAGE_TYPE_KEYWORD, type));
			}
		}
		HttpPingExecutor pingExecutor = new HttpPingExecutor();
		pingExecutor.ping(expandedUrls);
	}

}

