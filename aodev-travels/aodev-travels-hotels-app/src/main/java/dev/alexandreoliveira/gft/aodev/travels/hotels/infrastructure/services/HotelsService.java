package dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.services;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.HotelModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.RoomModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.hotels.create.HotelsCreateUseCase;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.hotels.index.HotelsIndexUseCase;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.create.RoomsCreateUseCase;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.entites.HotelEntity;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.entites.RoomEntity;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.repositories.HotelsRepository;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.repositories.RoomsRepository;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.rest.hotels.create.HotelsCreateControllerRequest;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.rest.hotels.create.HotelsCreateControllerResponse;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.rest.hotels.index.HotelsIntexControllerRequest;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.rest.hotels.index.HotelsIntexControllerResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = {Throwable.class})
public class HotelsService extends BaseService {

    private final HotelsCreateUseCase hotelsCreateUseCase;
    private final HotelsIndexUseCase hotelsIndexUseCase;
    private final RoomsCreateUseCase roomsCreateUseCase;

    public HotelsService(HotelsRepository repository, RoomsRepository roomsRepository) {
        this.hotelsCreateUseCase = new HotelsCreateUseCase(repository);
        this.hotelsIndexUseCase = new HotelsIndexUseCase(repository);
        this.roomsCreateUseCase = new RoomsCreateUseCase(roomsRepository);
    }

    public HotelsCreateControllerResponse create(HotelsCreateControllerRequest request) {
        var entity = new HotelEntity();
        entity.setName(request.name());
        entity.setCity(request.city());
        entity.setState(request.state());

        OutputDTO<HotelModel> output = hotelsCreateUseCase.execute(entity);

        hasErrors(output, "Erro ao criar um novo hotels");

        request.rooms().forEach(room -> {
            var roomEntity = new RoomEntity();
            roomEntity.setType(room.type());
            roomEntity.setBeds(room.beds());
            roomEntity.setPrice(room.price());
            roomEntity.setHotel(output.data());

            OutputDTO<RoomModel> outputRoom = roomsCreateUseCase.execute(roomEntity);
            hasErrors(outputRoom, "Error to create rooms for hotels.");
        });

        return new HotelsCreateControllerResponse(output.data().getId());
    }

    public HotelsIntexControllerResponse index(HotelsIntexControllerRequest request) {
        var entity = new HotelEntity();
        entity.setName(request.term());
        entity.setCity(request.term());
        entity.setState(request.term());
        OutputDTO<List<? extends HotelModel>> output = hotelsIndexUseCase.execute(entity);
        hasErrors(output, "Erro ao recuperar os hoteis");
        List<HotelsIntexControllerResponse.Hotel> hotels = output.data().stream().map(model -> {
            List<HotelsIntexControllerResponse.Room> rooms = model.getRooms().stream().map(roomModel -> new HotelsIntexControllerResponse.Room(roomModel.getId(), roomModel.getType(), roomModel.getBeds(), roomModel.getPrice())).toList();
            return new HotelsIntexControllerResponse.Hotel(model.getId(), model.getName(), model.getCity(), model.getState(), rooms);
        }).toList();
        return new HotelsIntexControllerResponse(hotels);
    }
}
