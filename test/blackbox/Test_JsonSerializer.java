package blackbox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pd.fenc.json.IJsonCreator;
import pd.fenc.json.IJsonObject;
import pd.fenc.json.IJsonValue;
import pd.fenc.json.JsonSerializer;

public class Test_JsonSerializer {

    private static final String src1 = "\"9\n3d$fs冬你我他\""; // 冬: 0x2F81A, 194586
    private static final String src2 = "{\"Consume\":{\"Moneys\":{\"totalMoney\":\"0.01\"},\"record\":[{\"AppID\":\"11\",\"CreateTime\":\"2008-09-05T08:49:18.063+08:00\",\"GiveEgg\":\"0\",\"ID\":\"714833524\",\"OrderState\":\"2\",\"OrderStateName\":\"失败\",\"OrderType\":\"3\",\"OrderTypeName\":\"实物交易订单\",\"PayID\":\"3\",\"PayWayID\":\"1\",\"PayWayName\":\"快钱在线支付\",\"TotalMoney\":\"0.01\",\"TotalNumber\":\"0\",\"TradeSource\":\"商城购物\",\"UserID\":\"668288112\"},{\"AppID\":\"11\",\"CreateTime\":\"2008-09-05T08:46:12.813+08:00\",\"GiveEgg\":\"0\",\"ID\":\"1350320533\",\"OrderState\":\"3\",\"OrderStateName\":\"处理中\",\"OrderType\":\"3\",\"OrderTypeName\":\"实物交易订单\",\"PayID\":\"7\",\"PayWayID\":\"2\",\"PayWayName\":\"XXX邮政储蓄所\",\"TotalMoney\":\"0.01\",\"TotalNumber\":\"0\",\"TradeSource\":\"商城购物\",\"UserID\":\"668288112\"},{\"AppID\":\"18\",\"CreateTime\":\"2008-09-05T08:40:55.703+08:00\",\"GiveEgg\":\"0\",\"ID\":\"1413965649\",\"OrderState\":\"1\",\"OrderStateName\":\"成功\",\"OrderType\":\"4\",\"OrderTypeName\":\"同步交易订单\",\"PayID\":\"2\",\"PayWayID\":\"1\",\"PayWayName\":\"财付通在线支付\",\"TotalMoney\":\"0.01\",\"TotalNumber\":\"0\",\"TradeSource\":\"同步课程\",\"UserID\":\"668288112\"},{\"AppID\":\"11\",\"CreateTime\":\"2008-09-05T08:39:29.127+08:00\",\"GiveEgg\":\"0\",\"ID\":\"83430389\",\"OrderState\":\"2\",\"OrderStateName\":\"失败\",\"OrderType\":\"3\",\"OrderTypeName\":\"实物交易订单\",\"PayID\":\"3\",\"PayWayID\":\"1\",\"PayWayName\":\"快钱在线支付\",\"TotalMoney\":\"0.01\",\"TotalNumber\":\"0\",\"TradeSource\":\"商城购物\",\"UserID\":\"668288112\"},{\"AppID\":\"11\",\"CreateTime\":\"2008-09-05T08:38:33.28+08:00\",\"GiveEgg\":\"0\",\"ID\":\"1206699325\",\"OrderState\":\"2\",\"OrderStateName\":\"失败\",\"OrderType\":\"3\",\"OrderTypeName\":\"实物交易订单\",\"PayID\":\"3\",\"PayWayID\":\"1\",\"PayWayName\":\"快钱在线支付\",\"TotalMoney\":\"0.01\",\"TotalNumber\":\"0\",\"TradeSource\":\"商城购物\",\"UserID\":\"668288112\"},{\"AppID\":\"11\",\"CreateTime\":\"2008-09-05T08:31:54.453+08:00\",\"GiveEgg\":\"0\",\"ID\":\"795858378\",\"OrderState\":\"2\",\"OrderStateName\":\"失败\",\"OrderType\":\"3\",\"OrderTypeName\":\"实物交易订单\",\"PayID\":\"3\",\"PayWayID\":\"1\",\"PayWayName\":\"快钱在线支付\",\"TotalMoney\":\"0.01\",\"TotalNumber\":\"0\",\"TradeSource\":\"商城购物\",\"UserID\":\"668288112\"}],\"results\":{\"totalRecords\":\"6\"}}}";

    @Test
    private void testCreator() {
        IJsonCreator creator = JsonSerializer.creator;
        IJsonObject jsonObject = creator.newJsonObject()
                .put("jj", creator.newJsonString(src1))
                .put("dd", creator.newJsonNumber(77))
                .put("vv", creator.newJsonArray().insert(creator.newJsonString("vbvb")));
        String jsonString = JsonSerializer.serialize(jsonObject);

        IJsonObject jsonObject2 = JsonSerializer.deserialize(jsonString, IJsonObject.class);
        String jsonString2 = JsonSerializer.serialize(jsonObject2);

        assertEquals(jsonString, jsonString2);
    }

    @Test
    public void testGet() {
        IJsonObject jsonObject = JsonSerializer.deserialize(src2, IJsonObject.class);
        String userId = jsonObject.getAsJsonObject("Consume")
                .getAsJsonArray("record")
                .getAsJsonObject(5)
                .getAsJsonString("UserID").value();
        assertEquals("668288112", userId);
    }

    private void testParseAndSerialize(String jsonCode) {
        IJsonValue jsonValue1 = JsonSerializer.deserialize(jsonCode);
        String jsonCode1 = JsonSerializer.serialize(jsonValue1);

        assertEquals(jsonCode, jsonCode1);

        IJsonValue jsonValue2 = JsonSerializer.deserialize(jsonCode1);
        String jsonCode2 = JsonSerializer.serialize(jsonValue2);

        assertEquals(jsonCode, jsonCode2);
    }

    @Test
    public void testSerializeAndDeserialize1() {
        testParseAndSerialize(src1);
    }

    @Test
    public void testSerializeAndDeserialize2() {
        testParseAndSerialize(src2);
    }
}
