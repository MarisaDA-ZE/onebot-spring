package top.kirisamemarisa.onebotspring.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: MarisaDAZE
 * @Description: 涩涩实体Vo
 * @Date: 2024/3/19
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SexEntityVo<T> {
    private boolean status;
    private String error;
    private T data;

    /**
     * 正确的
     *
     * @param data .
     * @param <T>  .
     * @return .
     */
    public static <T> SexEntityVo<T> success(T data) {
        return new SexEntityVo<>(true, null, data);
    }

    /**
     * 错误
     *
     * @param err 错误描述
     * @return 错误的
     */
    public static <T> SexEntityVo<T> error(String err) {
        return new SexEntityVo<>(false, err, null);
    }
}
