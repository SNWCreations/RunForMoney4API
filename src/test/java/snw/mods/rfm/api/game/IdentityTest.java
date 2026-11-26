package snw.mods.rfm.api.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentityTest {
    @Test
    void baseIdentityIsBasedOnItself() {
        for (Identity identity : Identity.values()) {
            assertTrue(identity.isBasedOn(identity), identity + " must be based on itself");
        }
    }

    @Test
    void derivedIdentityIsBasedOnItsUnderlyingIdentity() {
        assertTrue(Identity.OUT.isBasedOn(Identity.RUNNER));
        assertSame(Identity.RUNNER, Identity.OUT.getUnderlying());
    }

    @Test
    void baseIdentityIsNotBasedOnDerivedIdentity() {
        assertFalse(Identity.RUNNER.isBasedOn(Identity.OUT),
                "a base identity must not match identities derived from it");
    }

    @Test
    void unrelatedIdentitiesDoNotMatch() {
        assertFalse(Identity.HUNTER.isBasedOn(Identity.RUNNER));
        assertFalse(Identity.OUT.isBasedOn(Identity.HUNTER));
        assertFalse(Identity.NPC.isBasedOn(Identity.ABANDONER));
    }
}
