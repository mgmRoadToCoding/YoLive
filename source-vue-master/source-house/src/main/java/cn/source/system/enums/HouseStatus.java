package cn.source.system.enums;

/**
 * 操作人类别
 *
 * @author 詹Sir
 */
public enum HouseStatus
    {
        AUDIT(0, "待审核") , SALEING(1, "待出租"), SALEED(2, "已出租"), CLOSE(3, "已下架");
        private final Integer code;
        private final String info;

        HouseStatus(Integer code, String info)
        {
            this.code = code;
            this.info = info;
        }

        public Integer getCode()
        {
            return code;
        }

        public String getInfo()
        {
            return info;
        }
    }
