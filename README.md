# cloud-itonami-iso3166-cri

Open ISO 3166 Blueprint for **CRI**: Costa Rica -- **`:implemented`**.

This repository designs **and implements** a forkable OSS business for
an independent public-sector market-entry consultant: an already-
incorporated operator (e.g. a `cloud-itonami-cofog-{code}`,
`cloud-itonami-isco-{code}`, `cloud-itonami-unspsc-{segment}` or
`cloud-itonami-{ISIC}` blueprint fork) gets a **MarketEntry-LLM**
Compliance Advisor + independent **Market-Entry Compliance Governor**
to navigate public-procurement registration (SICOP), local business/
tax registration (Registro Nacional / Registro Mercantil, cédula
jurídica), and local-content rules in Costa Rica, so the operator can
win and service a government contract without hiring a full in-house
compliance department.

## Checks

Five checks, in priority order, evaluated by `marketentry.governor` on
every `MarketEntry-LLM` proposal. The first four are HARD violations a
human approver cannot override; double-actuation guards are counted
separately. The confidence/actuation gate (item 5) is SOFT -- but see
Actuation below, `:filing/draft`/`:filing/submit` never auto-commit
regardless.

| # | Check | Grounds | Source |
|---|---|---|---|
| 1 | **Spec-basis** -- a `:jurisdiction/assess`/`:filing/draft`/`:filing/submit` proposal must cite an official source, never an invented one | `marketentry.facts/spec-basis` | this repo's own `statute.facts` (Código de Comercio Ley N.º 3284), cgr.go.cr |
| 2 | **Evidence incomplete** -- for draft/submit, the jurisdiction's full required-evidence checklist must be on file: (a) Registro Mercantil inscription, (b) cédula jurídica tax registration, (c) SICOP Registro de Proveedores inscription, (d) patente municipal | `marketentry.facts/required-evidence-satisfied?` | Código de Comercio Ley N.º 3284 (reused from `statute.facts`); SICOP/Hacienda cited institutionally only |
| 3 | **PRODHAB registration scope mismatch** (flagship, scope-conditional) -- for submit, INDEPENDENTLY verify whether the engagement's own declared `:data-handling-scope` (distribute/disclose/commercialize personal data) actually requires PRODHAB database registration under Ley N.º 8968, and whether that duty was actually discharged; fires ONLY when the scope triggers the duty AND the engagement is not an exempt regulated financial institution AND registration is not on file -- NEVER for `:internal-only` use, NEVER for an exempt financial institution, even if unregistered -- not a blanket rule against any scope | `marketentry.governor/prodhab-registration-scope-mismatch-violations` | Ley de Protección de la Persona frente al Tratamiento de sus Datos Personales, Ley N.º 8968 -- the SAME citation as this repo's own `statute.facts/cri.ley-proteccion-datos-8968` -- corroborated via DLA Piper Data Protection Laws of the World (fetched this session) |
| 4 | **Engagement fee mismatch** -- for submit, independently recompute `claimed-fee = base-fee + monthly-rate x monitoring-months` | `marketentry.registry/engagement-fee-matches-claim?` | ground-truth recompute (fleet-standard discipline) |
| 5 | **Confidence floor / actuation gate** (SOFT) -- LLM confidence below 0.6, or the op is `:filing/draft`/`:filing/submit` -> escalate to human | `marketentry.governor/check` | this vertical's own Trust Controls (`docs/business-model.md`) |

Two further double-actuation guards (`already-drafted`,
`already-submitted`) refuse to draft or submit the SAME engagement
twice, enforced off dedicated `:drafted?`/`:submitted?` booleans, never
a `:status` value.

Check 3 is deliberately **scope-conditional, not a blanket rule**:
`test/marketentry/governor_contract_test.clj`'s
`prodhab-registration-scope-mismatch-does-not-fire-when-registered`
proves a `:commercialize`-scope engagement that IS PRODHAB-registered
proceeds through the ordinary escalate-then-approve path with no HARD
hold, `prodhab-registration-scope-mismatch-does-not-fire-for-exempt-financial-institution`
proves an unregistered but exempt regulated financial institution also
never triggers a HARD hold, while
`prodhab-registration-scope-mismatch-is-held-and-unoverridable` proves
the SAME `:commercialize` scope on an unregistered, non-exempt
engagement is an un-overridable HARD hold. Three independent,
contrasting fixtures (`eng-4`, `eng-5`, `eng-6`) in
`marketentry.store/demo-data` exercise all three branches.

**Costa Rica's data-protection law conditionally attaches an
ADDITIONAL registration duty onto a public-procurement market-entry
filing, a structural fact this repository confirmed against a
corroborating source this session, not merely assumed:** Ley N.º 8968
(this repo's own already-established `statute.facts` citation) charges
PRODHAB (Agencia de Protección de Datos de los Habitantes) with
enforcing that "companies that manage databases containing personal
information and that distribute, disclose or commercialize such
personal information in any manner must register with the Agency,"
while "entities that manage databases containing personal information
for internal purposes do not need to be registered with PRODHAB," and
financial institutions under separate regulatory oversight are also
exempt (per DLA Piper Data Protection Laws of the World, fetched and
read directly this session -- prodhab.go.cr itself returned HTTP 403
Forbidden on direct fetch this session, disclosed, not concealed).
`marketentry.facts/prodhab-registration-required?` exposes exactly
this scope-trigger/exemption logic -- an unrecognized scope keyword is
NEVER assumed to trigger (or not trigger) registration; see
`test/marketentry/facts_test.clj`'s
`prodhab-registration-required-is-honestly-scoped`.

**Costa Rica's Registro Nacional is, by contrast, a single national
registry** (per this repo's own pre-existing `organization.edn` /
`docs/business-model.md`, and no source reachable this session
suggested otherwise) -- unlike Honduras's Cámara de Comercio system,
which is split department-by-department. This repo therefore does
NOT build a territorial-jurisdiction-mismatch check (that would be a
fabricated premise for an undecentralized registry); its flagship
check targets the genuinely different, independently-corroborated
PRODHAB mechanism instead.

## Actuation

**Drafting a real SICOP filing / portal registration and submitting a
real filing are never autonomous, at any phase, by construction.** Two
independent layers enforce this:

- `marketentry.governor`'s `high-stakes` set
  (`#{:actuation/draft-filing :actuation/submit-filing}`) always
  escalates, regardless of confidence.
- `marketentry.phase`'s phase table (`phase 0` through `phase 3`)
  never puts `:filing/draft` or `:filing/submit` in any phase's
  `:auto` set -- see `marketentry.phase`'s own docstring and
  `test/marketentry/phase_test.clj`'s `filing-submit-never-auto`, plus
  `test/marketentry/governor_contract_test.clj`'s
  `filing-draft-and-submit-never-auto-commit`.

The actor may intake an engagement, assess a jurisdiction and draft a
recommendation; a human market-entry operator is always the one who
actually files a draft or a submission. Grounded directly in this
blueprint's own [`docs/business-model.md`](docs/business-model.md) and
`marketentry.governor`'s own namespace docstring, which names this
vertical's Trust Controls verbatim: "any actual portal registration or
filing submission requires Market-Entry Compliance Governor clearance
and always escalates to human sign-off"; "a false or fabricated
regulatory-requirement claim is a HARD hold". `:filing/draft` and
`:filing/submit` apply SEQUENTIALLY to the SAME engagement record
(draft first, submit later) -- matching every sibling
`market-entry-compliance-governor` actor's own sequential shape.

## No robotics premise — digital/data service exemption

Market-entry and procurement-compliance navigation is a pure data/software
service with no physical-domain work (portal registration, document
checklists, regulatory-change monitoring) — the same exemption class as
`cloud-itonami-6310` (HR SaaS replacement) and `cloud-itonami-gtin-*`.
`blueprint.edn` sets `:itonami.blueprint/robotics false` and
`:required-technologies` lists only real capabilities (`:identity`,
`:forms`, `:dmn`, `:bpmn`, `:audit-ledger`), no `:robotics`.

## Core Contract

```text
operator intake + prior filing history
        |
        v
Compliance Advisor -> Market-Entry Compliance Governor -> filing draft, or human sign-off
        |
        v
gated portal registration / filing submission + audit ledger
```

No automated proposal can submit a portal registration or filing the
governor refuses, suppress a compliance record, or claim a legal/tax
conclusion the governor has not cleared. `:filing/submit` is never in any
phase's `:auto` set — it always requires human sign-off (mirrors
`cloud-itonami-M6910`'s `filing-submit-never-auto-at-any-phase`
invariant).

## What this is NOT

- **Not the government of Costa Rica.** See
  [`docs/business-model.md`](docs/business-model.md) for the boundary with
  `com-etzhayyim-ooyake` (read-only civic mirror), `matsurigoto` (sovereign
  statecraft), `com-etzhayyim-toritsugi` (individual citizen concierge),
  `legal-entity.etzhayyim.com` (read-only data aggregation), and
  `cloud-itonami-M6910` (company incorporation — a different regulatory
  phase this blueprint assumes is already complete).
- **Not legal or tax advice.** Every regulatory claim must cite the
  official source and route final filings to Costa Rican-licensed counsel
  or a registered agent where the law requires licensed representation.

## Capability layer

Resolves via [`kotoba-lang/iso3166`](https://github.com/kotoba-lang/iso3166)
(ISO 3166 `CRI`). Required capabilities:

- :identity
- :forms
- :dmn
- :bpmn
- :audit-ledger

See [`docs/business-model.md`](docs/business-model.md) and
[`docs/operator-guide.md`](docs/operator-guide.md).

## Run

```bash
clojure -M:dev:run     # walk a clean intake -> assess -> draft -> submit lifecycle, plus HARD-hold scenarios (PRODHAB registration-scope mismatch included)
clojure -M:dev:test    # governor contract · phase invariants · store parity · registry conformance · facts coverage
clojure -M:lint        # clj-kondo (errors fail; CI mirrors this)
```

## License

AGPL-3.0-or-later.

## Market-entry / statute catalogs

Governed public-sector market-entry compliance actor, same architecture
as the other `cloud-itonami-iso3166-*` siblings:

- `src/marketentry/{facts,governor,phase,sim,operation,registry,store,
  marketentryllm}.cljc` -- the actor. `facts.cljc` reuses this repo's
  own `src/statute/facts.cljc` citations verbatim (Código de Comercio
  Ley N.º 3284 for the mercantile-registration precondition, Ley de
  Protección de Datos Ley N.º 8968 for the flagship PRODHAB check),
  never a second, different citation for the same law, and cites
  Contraloría General de la República (cgr.go.cr, fetched and read
  directly this session) at the confirmed institutional/oversight
  level. `governor.cljc`'s flagship check independently verifies
  whether an engagement's own declared personal-data-handling scope
  actually triggers a PRODHAB database-registration duty under Ley
  8968 that the engagement has not discharged -- a check SHAPE
  genuinely different from siblings whose flagship check is a
  sector-conditional constitutional restriction (Panama), a
  cross-branch certification-threshold gate (Nicaragua), or a
  multi-authority territorial-jurisdiction consistency test
  (Honduras): this one is a CROSS-STATUTE, SCOPE-CONDITIONAL
  applicability gate, grounded in a country whose data-protection law
  conditionally attaches an additional duty onto a public-procurement
  filing (see the namespace docstrings and
  `test/marketentry/governor_contract_test.clj`'s three contrasting
  fixtures for the full honest disclosure).
- `src/statute/facts.cljc` -- general-law catalog (pre-existing, not
  modified by this Wave): Código de Comercio (Ley N.º 3284), Ley de
  Protección de Datos (Ley N.º 8968), Código de Trabajo (Ley N.º 2).

Every citation either reuses this repo's own already-verified
`statute.facts` entries, or is fetch-verified against a source this
session actually read (cgr.go.cr; DLA Piper Data Protection Laws of
the World for the PRODHAB registration mechanism; the pgrweb.go.cr ->
sinalevi.go.cr redirect confirming the existing Código de Comercio
citation's underlying record is still live). Sources that were
unreachable this session -- sicop.go.cr (empty client-rendered
shell), hacienda.go.cr (HTTP 400), rnpdigital.com (refused with an
IP-blocklist message, not bypassed), sinalevi.go.cr (TLS certificate
error), prodhab.go.cr (HTTP 403), mjp.go.cr/procomer.com/
asamblea.go.cr/imprentanacional.go.cr (404/403/connection refused) --
are named explicitly rather than guessed at, and are NOT cited as
independently-browsed live sources -- see `marketentry.facts`'s own
docstring for the full honest disclosure of which citation is a
live-verified URL vs. a reused prior-session citation vs. a disclosed
reachability gap.

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) — national dishes, protected products, beverages,
crafts, festivals and heritage sites for Costa Rica:

- `src/culture/facts.cljc` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.
