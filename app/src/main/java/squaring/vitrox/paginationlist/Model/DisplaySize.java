package squaring.vitrox.paginationlist.Model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class DisplaySize
{
    @JsonProperty("uri")
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}