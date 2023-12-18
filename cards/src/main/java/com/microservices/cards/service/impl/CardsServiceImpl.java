package com.microservices.cards.service.impl;

import com.microservices.cards.constants.CardsConstants;
import com.microservices.cards.entity.Cards;
import com.microservices.cards.exception.CardAlreadyExistException;
import com.microservices.cards.model.CardsDTO;
import com.microservices.cards.repository.CardsRepository;
import com.microservices.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;
    @Override
    public void createCard(String mobileNumber){
        Optional<Cards> byMobileNumber = cardsRepository.findByMobileNumber(mobileNumber);
        if (byMobileNumber.isPresent()){
            throw new CardAlreadyExistException("Card already registered with the given mobile number {}", mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardsDTO fetchCard(String mobileNumber) {
        return null;
    }

    @Override
    public boolean updateCard(CardsDTO cardsDto) {
        return false;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        return false;
    }
}
