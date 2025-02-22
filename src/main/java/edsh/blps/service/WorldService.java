package edsh.blps.service;

import edsh.blps.Repository.Warehouse_address_Repository;
import edsh.blps.Repository.World_address_Repository;
import edsh.blps.entity.World_address;
import org.springframework.stereotype.Service;

@Service
public class WorldService {
    private final World_address_Repository world_address_Repository;

    public WorldService(World_address_Repository worldAddressRepository) {
        this.world_address_Repository = worldAddressRepository;
    }

    public World_address getAddress(String address) {
        System.out.println(address);
        return world_address_Repository.findByAddress(address).get(0);
    }
}

