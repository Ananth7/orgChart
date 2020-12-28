package personio.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.StringJoiner;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
