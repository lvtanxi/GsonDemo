package com.lv.gsondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.lv.gsondemo.model.DateModel;
import com.lv.gsondemo.model.ExposeModel1;
import com.lv.gsondemo.model.User;
import com.lv.gsondemo.model.User2;
import com.lv.gsondemo.model.User3;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DLog.init("lv", true);
    }

    public void testSerializedName(View view) {
        //请注意每个类中的SerializedName
        Gson gson = new Gson();
        String jsonObject1 = gson.toJson(new User());
        String jsonObject2 = gson.toJson(new User3());
        User2 user2 = gson.fromJson(jsonObject1, User2.class);
        DLog.d(user2);
        User2 user22 = gson.fromJson(jsonObject2, User2.class);
        DLog.d(user22);
    }

    public void testNormal(View view) {
        Gson gson = new Gson();
        String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
        String[] strings = gson.fromJson(jsonArray, String[].class);
        DLog.d(ArrayUtils.arrayEmpty(strings));
        List<String> stringList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {
        }.getType());
        DLog.d(ArrayUtils.isEmpty(stringList));
    }

    public void testJsonReader(View view) {
        User3 user3 = new User3();
        Gson gson = new Gson();
        String s = gson.toJson(user3);
        User3 user33 = new User3();
        JsonReader jsonReader = new JsonReader(new StringReader(s));
        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                switch (nextName) {
                    case "name":
                        user33.name = jsonReader.nextString();
                        break;
                    case "address":
                        user33.address = jsonReader.nextString();
                        break;
                    case "age":
                        user33.age = jsonReader.nextInt();
                        break;
                    default:
                        break;
                }
            }
            jsonReader.endObject();
            jsonReader.close();
            DLog.d(user33);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void testJsonWriter(View view) {
        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(System.out));
        try {
            jsonWriter.beginObject()
                    .name("name").value("lv")
                    .name("age").value(24)
                    .name("email").nullValue() //表示强制输出null
                    .endObject();
            jsonWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testGsonBuilder(View view) {
        //利用GsonBuilder生成我们部分自定义的Gson;例如导出null值、格式化输出、日期时间
        Gson gson = new GsonBuilder()
                //序列化null
                .serializeNulls()
                // 设置日期时间格式，另有2个重载方法
                // 在序列化和反序化时均生效
                .setDateFormat("yyyy-MM-dd")
                // 禁此序列化内部类
                .disableInnerClassSerialization()
                //生成不可执行的Json（多了 )]}' 这4个字符）
                .generateNonExecutableJson()
                //禁止转义html标签
                .disableHtmlEscaping()
                //格式化输出
                .setPrettyPrinting()
                .create();
        DateModel dateModel = new DateModel();
        DLog.d(gson.toJson(dateModel));
    }

    /**
     * @Expose //
     * @Expose(deserialize = true,serialize = true) //序列化和反序列化都都生效
     * @Expose(deserialize = true,serialize = false) //反序列化时生效
     * @Expose(deserialize = false,serialize = true) //序列化时生效
     * @Expose(deserialize = false,serialize = false) // 和不写一样
     */
    public void testExpose(View view) {
        Gson gson = new Gson();
        DLog.d(gson.toJson(new ExposeModel1()));
        Gson g2 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        DLog.d(g2.toJson(new ExposeModel1()));
    }



    /**
     * 前方高能
     * TypeAdapter与 JsonSerializer、JsonDeserializer对比
     * TypeAdapter	JsonSerializer、JsonDeserializer
     * 引入版本	            2.0                      	1.x
     * Stream API  	 支持	                不支持*，需要提前生成JsonElement
     * 内存占用	            小	                比TypeAdapter大
     * 效率	                高	                比TypeAdapter低
     * 作用范围	    序列化 和 反序列化	序列化 或 反序列化
     */


    public void testTypeAdapter(View view) {
        User3 user33 = new User3();
        Gson gson = new GsonBuilder().registerTypeAdapter(User3.class, new TypeAdapter<User3>() {

            @Override
            public void write(JsonWriter out, User3 value) throws IOException {
                out.beginObject();
                out.name("name").value(value.name);
                out.name("age").value(value.age);
                out.name("email").value(value.address);
                out.endObject();
            }

            @Override
            public User3 read(JsonReader jsonReader) throws IOException {
                User3 user3 = new User3();
                try {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String nextName = jsonReader.nextName();
                        switch (nextName) {
                            case "name":
                                user3.name = jsonReader.nextString();
                                break;
                            case "address":
                                user3.address = jsonReader.nextString();
                                break;
                            case "age":
                                user3.age = jsonReader.nextInt();
                                break;
                            default:
                                break;
                        }
                    }
                    jsonReader.endObject();
                    jsonReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return user3;
            }
        }).create();
        DLog.d(gson.toJson(user33));
    }

    public void testJsonDeserializer(View view) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {
                    @Override
                    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        try {
                            return json.getAsInt();
                        } catch (NumberFormatException e) {
                            return -1;
                        }
                    }
                })
                .create();
        DLog.d(gson.toJson(100)); //结果：100
        DLog.d(gson.fromJson("A", Integer.class)); //结果-1
    }

    public void testJsonSerializer(View view) {
        JsonSerializer<Number> numberJsonSerializer = new JsonSerializer<Number>() {
            @Override
            public JsonElement serialize(Number src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(String.valueOf(src));
            }
        };
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, numberJsonSerializer)
                .registerTypeAdapter(Long.class, numberJsonSerializer)
                .registerTypeAdapter(Float.class, numberJsonSerializer)
                .registerTypeAdapter(Double.class, numberJsonSerializer)
                .create();
        DLog.d(gson.toJson(100.0f));//结果："100.0"
    }
}
