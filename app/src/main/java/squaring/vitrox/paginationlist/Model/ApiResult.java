package squaring.vitrox.paginationlist.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ApiResult {

    @JsonProperty("images")
    private List<ApiImage> Images;

    @JsonProperty("result_count")
    private int ResultCount;

    public List<ApiImage> getImages() {
        return Images;
    }

    public void setImages(List<ApiImage> images) {
        Images = images;
    }

    public int getResultCount() {
        return ResultCount;
    }

    public void setResultCount(int resultCount) {
        ResultCount = resultCount;
    }
}
