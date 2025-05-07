package edsh.blps.dto;

import java.util.List;

public class PickPointResponse {
    private List<PickPointDTO> pickPoints;

    public List<PickPointDTO> getPickPoints() {
        return pickPoints;
    }

    public void setPickPoints(List<PickPointDTO> pickPoints) {
        this.pickPoints = pickPoints;
    }
}
