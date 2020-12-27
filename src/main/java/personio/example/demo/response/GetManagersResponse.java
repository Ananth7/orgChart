package personio.example.demo.response;

import lombok.Data;

import java.util.StringJoiner;

@Data
public class GetManagersResponse {
    private String manager;
    private String skipLevelManager;

    @Override
    public String toString() {
        return new StringJoiner(", ",  "{", "}")
                .add("\"manager\":\"" + manager + "\"")
                .add("\"skipLevelManager\":\"" + skipLevelManager + "\"")
                .toString();
    }
}
