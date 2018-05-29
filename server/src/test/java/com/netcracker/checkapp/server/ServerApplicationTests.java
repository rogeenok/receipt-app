package com.netcracker.checkapp.server;

import com.netcracker.checkapp.server.model.DateStats;
import com.netcracker.checkapp.server.model.ShopStats;
import com.netcracker.checkapp.server.model.UserInfo;
import com.netcracker.checkapp.server.model.check.Check;
import com.netcracker.checkapp.server.model.check.Item;
import com.netcracker.checkapp.server.model.place.Coords;
import com.netcracker.checkapp.server.model.place.Place;
import com.netcracker.checkapp.server.model.place.ShortPlace;
import com.netcracker.checkapp.server.persistance.CheckRepository;
import com.netcracker.checkapp.server.persistance.PlaceRepository;
import com.netcracker.checkapp.server.persistance.UserInfoRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServerApplicationTests {

	@Autowired
	CheckRepository checkRepository;
	@Autowired
    PlaceRepository placeRepository;
	@Autowired
    UserInfoRepository userInfoRepository;

	@Test
	public void Test0contextLoads() {
	}

	@Test
    public void Test1addUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId("test-user-1");
        userInfo.setLogin("username-1");
        userInfo.setPwd("polytech119");
        userInfo.setRole("ROLE_USER");

        UserInfo userInfo1 = userInfoRepository.save(userInfo);
        assertThat(userInfo1).isEqualTo(userInfo);
    }

    @Test
    public void Test2addPlaceNumberOne() {
        Place place = new Place();
        place.setId("test-place-1");
        place.setName("place-name-1");
        place.setAddress("place-address-1");
        place.setNumOfChecks(0);
        Coords coords = new Coords();
        coords.setLatitude(60.0105776908439);
        coords.setLongitude(30.396507796142);
        place.setCoords(coords);

        Place place1 = this.placeRepository.insert(place);
        assertThat(place).isEqualTo(place1);
    }

    @Test
    public void Test2addPlaceNumberTwo() {
        Place place = new Place();
        place.setId("test-place-2");
        place.setName("place-name-2");
        place.setAddress("place-address-2");
        place.setNumOfChecks(0);
        Coords coords = new Coords();
        coords.setLatitude(60.0145667805233);
        coords.setLongitude(30.3884205079278);
        place.setCoords(coords);

        Place place2 = this.placeRepository.insert(place);
        assertThat(place).isEqualTo(place2);
    }

	@Test
	public void Test3addCheckNumberOne() {
	    Check check = new Check();
	    check.setUsername("username-1");
		check.setId("test-check-1");
		check.setFiscalDocumentNumber("DocNumber-1");
		check.setFiscalDriveNumber("DriveNumber-1");
		check.setFiscalSign("Sign-1");
		check.setDateTime(LocalDateTime.of(2018,04,5,15,10,5));

        Item item1 = new Item();
        item1.setName("item-1-check-1");
        item1.setPrice(BigDecimal.valueOf(100.50));
        item1.setNds18(item1.getPrice().multiply(BigDecimal.valueOf(0.18)));
        item1.setNdsSum(item1.getNds18().add(item1.getNds10()));
        item1.setQuantity(1.5);

        List<Item> items = new ArrayList<>();
        items.add(item1);
        check.setNds10(items.stream().map(Item::getNds10).reduce((nds101,nds102) -> nds101.add(nds102)).orElse(BigDecimal.ZERO));
        check.setNds18(items.stream().map(Item::getNds18).reduce((nds101,nds102) -> nds101.add(nds102)).orElse(BigDecimal.ZERO));
        check.setTotalSum(0);
        items.forEach(item -> {
            check.setTotalSum(check.getTotalSum() + (int)(item.getQuantity() * item.getPrice().doubleValue()));
        });
        check.setItems(items);

        ShortPlace shortPlace = new ShortPlace();
        shortPlace.setId("test-place-1");
        shortPlace.setName("place-name-1");
        Coords coords = new Coords();
        coords.setLatitude(60.0105776908439);
        coords.setLongitude(30.396507796142);
        shortPlace.setCoords(coords);
        check.setShortPlace(shortPlace);

	    Check check1 = this.checkRepository.insert(check);
        assertThat(check.getUsername()).isEqualTo(check1.getUsername());
	}

	@Test
    public void Test3addCheckNumberTwo() {
        Check check = new Check();
        check.setUsername("username-1");
        check.setId("test-check-2");
        check.setFiscalDocumentNumber("DocNumber-2");
        check.setFiscalDriveNumber("DriveNumber-2");
        check.setFiscalSign("Sign-2");
        check.setDateTime(LocalDateTime.of(2018,03,25,05,31,55));

        Item item1 = new Item();
        item1.setName("item-1-check-2");
        item1.setPrice(BigDecimal.valueOf(557));
        item1.setQuantity(1.0);
        item1.setNds10(item1.getPrice().multiply(BigDecimal.valueOf(item1.getQuantity())).multiply(BigDecimal.valueOf(0.10)));
        item1.setNdsSum(item1.getNds18().add(item1.getNds10()));

        Item item2 = new Item();
        item2.setName("item-2-check-2");
        item2.setPrice(BigDecimal.valueOf(35));
        item2.setQuantity(6.762);
        item2.setNds18(item2.getPrice().multiply(BigDecimal.valueOf(item2.getQuantity())).multiply(BigDecimal.valueOf(0.18)));
        item2.setNdsSum(item2.getNds18().add(item2.getNds10()));

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        check.setNds10(items.stream().map(Item::getNds10).reduce((nds101,nds102) -> nds101.add(nds102)).orElse(BigDecimal.ZERO));
        check.setNds18(items.stream().map(Item::getNds18).reduce((nds101,nds102) -> nds101.add(nds102)).orElse(BigDecimal.ZERO));
        check.setTotalSum(0);
        items.forEach(item -> {
            check.setTotalSum(check.getTotalSum() + (int)(item.getQuantity() * item.getPrice().doubleValue()));
        });
        check.setItems(items);

        ShortPlace shortPlace = new ShortPlace();
        shortPlace.setId("test-place-1");
        shortPlace.setName("place-name-1");
        Coords coords = new Coords();
        coords.setLatitude(60.0105776908439);
        coords.setLongitude(30.396507796142);
        shortPlace.setCoords(coords);
        check.setShortPlace(shortPlace);

        Check check2 = this.checkRepository.insert(check);
        assertThat(check.getUsername()).isEqualTo(check2.getUsername());
    }

    @Test
    public void Test3addCheckNumberThree() {
        Check check = new Check();
        check.setUsername("username-1");
        check.setId("test-check-3");
        check.setFiscalDocumentNumber("DocNumber-3");
        check.setFiscalDriveNumber("DriveNumber-3");
        check.setFiscalSign("Sign-3");
        check.setDateTime(LocalDateTime.of(2018,01,12,18,00,23));

        Item item1 = new Item();
        item1.setName("item-1-check-3");
        item1.setPrice(BigDecimal.valueOf(1500));
        item1.setQuantity(1.0);
        item1.setNds10(item1.getPrice().multiply(BigDecimal.valueOf(item1.getQuantity())).multiply(BigDecimal.valueOf(0.10)));
        item1.setNdsSum(item1.getNds18().add(item1.getNds10()));

        Item item2 = new Item();
        item2.setName("item-2-check-3");
        item2.setPrice(BigDecimal.valueOf(172.50));
        item2.setQuantity(2.0);
        item2.setNds18(item2.getPrice().multiply(BigDecimal.valueOf(item2.getQuantity())).multiply(BigDecimal.valueOf(0.18)));
        item2.setNdsSum(item2.getNds18().add(item2.getNds10()));

        Item item3 = new Item();
        item3.setName("item-2-check-3");
        item3.setPrice(BigDecimal.valueOf(23.00));
        item3.setQuantity(10.0);
        item3.setNds18(item3.getPrice().multiply(BigDecimal.valueOf(item3.getQuantity())).multiply(BigDecimal.valueOf(0.18)));
        item3.setNdsSum(item3.getNds18().add(item3.getNds10()));

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        check.setNds10(items.stream().map(Item::getNds10).reduce((nds101,nds102) -> nds101.add(nds102)).orElse(BigDecimal.ZERO));
        check.setNds18(items.stream().map(Item::getNds18).reduce((nds101,nds102) -> nds101.add(nds102)).orElse(BigDecimal.ZERO));
        check.setTotalSum(0);
        items.forEach(item -> {
            check.setTotalSum(check.getTotalSum() + (int)(item.getQuantity() * item.getPrice().doubleValue()));
        });
        check.setItems(items);

        ShortPlace shortPlace = new ShortPlace();
        shortPlace.setId("test-place-2");
        shortPlace.setName("place-name-2");
        Coords coords = new Coords();
        coords.setLatitude(60.0145667805233);
        coords.setLongitude(30.3884205079278);
        shortPlace.setCoords(coords);
        check.setShortPlace(shortPlace);

        Check check3 = this.checkRepository.insert(check);
        assertThat(check.getUsername()).isEqualTo(check3.getUsername());
    }

    @Test
    public void Test4getStats1Shops() {
	    ShopStats shopStats1 = new ShopStats();
	    shopStats1.setId("place-name-1");
	    shopStats1.setMin(150);
	    shopStats1.setMax(793);
	    shopStats1.setAvg(471.5);
	    shopStats1.setSum(943);

        ShopStats shopStats2 = new ShopStats();
        shopStats2.setId("place-name-2");
        shopStats2.setMin(2075);
        shopStats2.setMax(2075);
        shopStats2.setAvg(2075.0);
        shopStats2.setSum(2075);

        List<ShopStats> shopStatsList = new ArrayList<>();
        shopStatsList.add(shopStats1);
        shopStatsList.add(shopStats2);

        List<ShopStats> shopStatsList1 = checkRepository.getShopStats("username-1");
        assertThat(shopStatsList1).isEqualTo(shopStatsList);
    }

    @Test
    public void Test4getStats2Dates() {
        DateStats dateStats1 = new DateStats();
        dateStats1.setId("3");
        dateStats1.setSum(793);

        DateStats dateStats2 = new DateStats();
        dateStats2.setId("1");
        dateStats2.setSum(2075);

        DateStats dateStats3 = new DateStats();
        dateStats3.setId("4");
        dateStats3.setSum(150);

        List<DateStats> dateStatsList = new ArrayList<>();
        dateStatsList.add(dateStats1);
        dateStatsList.add(dateStats2);
        dateStatsList.add(dateStats3);

        List<DateStats> dateStatsList1 = checkRepository.getDateStats("username-1");
        assertThat(dateStatsList1).isEqualTo(dateStatsList);
    }

	@Test
    public void Test5clearTestResults() {
        placeRepository.delete("test-place-1");
        placeRepository.delete("test-place-2");
        checkRepository.delete("test-check-1");
        checkRepository.delete("test-check-2");
        checkRepository.delete("test-check-3");
        userInfoRepository.delete("test-user-1");

        assertThat(placeRepository.findOne("test-place-1")).isEqualTo(null);
        assertThat(placeRepository.findOne("test-place-2")).isEqualTo(null);
        assertThat(checkRepository.findOne("test-check-1")).isEqualTo(null);
        assertThat(checkRepository.findOne("test-check-2")).isEqualTo(null);
        assertThat(checkRepository.findOne("test-check-3")).isEqualTo(null);
        assertThat(userInfoRepository.findOne("username-1")).isEqualTo(null);
    }
}
